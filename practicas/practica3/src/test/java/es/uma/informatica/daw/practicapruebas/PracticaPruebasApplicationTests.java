package es.uma.informatica.daw.practicapruebas;

import es.uma.informatica.daw.practicapruebas.dtos.CitaDTO;
import es.uma.informatica.daw.practicapruebas.dtos.Mapper;
import es.uma.informatica.daw.practicapruebas.entidades.Cita;
import es.uma.informatica.daw.practicapruebas.entidades.EstadoCita;
import es.uma.informatica.daw.practicapruebas.repositorios.RepositorioCitas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestRestTemplate
@DisplayName("En el servicio de citas")
class PracticaPruebasApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    private RepositorioCitas repositorioCitas;

    private String url(String rutaYConsulta) {
        return "http://localhost:" + port + rutaYConsulta;
    }

    public Cita createCita(String cliente, String inicio, int duracion){
        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setInicio(LocalDateTime.parse("2026-04-30T10:00:00"));
        cita.setDuracion(duracion);

        return cita;
    }

    @Nested
    @DisplayName("Tests creacion de cita")
    public class CreacionCita {
        @Test
        @DisplayName("devuelve error al insertar una cita con duración menor que la mínima")
        public void insertarCitaDemasiadoCorta() {
            Cita cita = createCita("Juan", "2026-04-30T10:00:00", 14);

            ResponseEntity<CitaDTO> res = restTemplate.postForEntity(
                    url("/citas"),
                    cita,
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(400);
        }

        @Test
        @DisplayName("devuelve error al insertar una cita con duración mayor que la máxima")
        public void insertarCitaDemasiadoLarga() {
            // Guarda una cita para el 30 de abril a las 10:00am de 1 hora
            Cita cita = createCita("Juan", "2026-04-30T10:00:00", 121);

            ResponseEntity<CitaDTO> res = restTemplate.postForEntity(
                    url("/citas"),
                    cita,
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(400);
        }

        @Test
        @DisplayName("devuelve error al insertar una cita antes del horario laboral")
        public void insertarCitaAntesDelComienzoHorario() {
            Cita cita = createCita("Juan", "2026-04-30T07:00:00", 60);

            ResponseEntity<CitaDTO> res = restTemplate.postForEntity(
                    url("/citas"),
                    cita,
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(400);
        }

        @Test
        @DisplayName("devuelve error al insertar una cita despues del horario laboral")
        public void insertarCitaDespuesDelFinalHorario() {
            Cita cita = createCita("Juan", "2026-04-30T20:00:00", 60);

            ResponseEntity<CitaDTO> res = restTemplate.postForEntity(
                    url("/citas"),
                    cita,
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(400);
        }

        @Test
        @DisplayName("devuelve error al insertar una cita con solapamiento")
        public void insertarCitaConSolapamiento() {
            Cita cita = createCita("Juan", "2026-04-30T10:00:00", 60);
            repositorioCitas.save(cita);

            ResponseEntity<CitaDTO> res = restTemplate.postForEntity(
                    url("/citas"),
                    cita,
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(409);
        }

        @Test
        @DisplayName("Inserción correcta de la cita")
        public void insertarCitaCorrecta() {
            Cita cita = createCita("Juan", "2026-04-30T10:00:00", 60);

            ResponseEntity<CitaDTO> res = restTemplate.postForEntity(
                    url("/citas"),
                    cita,
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(201);
            assertThat(res.getHeaders().get("Location").get(0)).isEqualTo("http://localhost:"+port+"/citas/1");
        }
    }

    @Nested
    @DisplayName("Tests obtener cita")
    public class ObtenerCita {

        @Test
        @DisplayName("al buscar por id 1 cuando ssolo existe una cita, funciona")
        void buscarExistente() {
            Cita cita = createCita("Juan", "2026-04-30T10:00:00", 60);
            repositorioCitas.save(cita);

            ResponseEntity<CitaDTO> res = restTemplate.getForEntity(
                    url("/citas/1"),
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(200);
            assertThat(res.getBody().getCliente()).isEqualTo(cita.getCliente());
            assertThat(res.getBody().getDuracion()).isEqualTo(cita.getDuracion());
        }

        @Test
        @DisplayName("error al buscar un id inexistente")
        void buscarInexistente() {
            ResponseEntity<CitaDTO> res = restTemplate.getForEntity(
                    url("/citas/1"),
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(404);
        }
    }

    @Nested
    @DisplayName("Tests confirmación de cita")
    public class ConfirmarCita {

        @Test
        @DisplayName("Confirmar cita existente")
        public void confirmarCitaExistente(){
            Cita cita = createCita("Juan", "2026-04-30T10:00:00", 60);
            cita.setEstado(EstadoCita.CREADA);
            repositorioCitas.save(cita);

            ResponseEntity<CitaDTO> res = restTemplate.postForEntity(
                    url("/citas/1/confirmar"),
                    null,
                    CitaDTO.class
            );

            assertThat(res.getStatusCode().value()).isEqualTo(200);
            assertThat(res.getBody().getEstado()).isEqualTo(EstadoCita.CONFIRMADA);
            assertThat(res.getBody().getCliente()).isEqualTo(cita.getCliente());
            assertThat(res.getBody().getDuracion()).isEqualTo(cita.getDuracion());
        }
    }

    @Nested
    @DisplayName("Tests obtener cita por fecha")
    public class ConsultarCitaPorFecha {
        @Test
        @DisplayName("al buscar por fecha se encuentran las citas de ese día")
        void buscarPorFecha() {
            // Guarda una cita para el 30 de abril a las 10:00am de 1 hora
            Cita cita = createCita("Juan", "2026-04-30T10:00:00", 60);
            repositorioCitas.save(cita);

            // Consulta las citas del 30 de abril
            ResponseEntity<CitaDTO[]> res = restTemplate.getForEntity(
                    url("/citas?fecha=2026-04-30"),
                    CitaDTO[].class
            );

            // Comprueba que hay una
            assertThat(res.getBody()).hasSize(1);
        }
    }

}
