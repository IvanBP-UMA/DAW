package es.uma.informatica.daw.llama.servicios;

import es.uma.informatica.daw.llama.dtos.TareaDTO;
import es.uma.informatica.daw.llama.seguridad.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioConsulta {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Value("${baseUrl}")
    private String baseUrl;

    public ServicioConsulta(RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }

    public List<TareaDTO> obtenerTareasConsultadas(Long id) {
        if (id == null) {
            // Caso A: Obtener lista completa (Uso de Arrays por el borrado de genéricos en Java)
            ResponseEntity<TareaDTO[]> response =
                    restTemplate.getForEntity(baseUrl + "/api/tareas", TareaDTO[].class);

            return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
        } else {
            // Caso B: Petición con Seguridad (JWT)
            // Generamos token
            String token = jwtUtil.generateToken("-1");
            System.out.println("DEBUG TOKEN: " + token);

            // Construimos la RequestEntity para incluir el header de Authorization
            RequestEntity<Void> request = RequestEntity.get(baseUrl + "/api/tareas/" + id)
                    .header("Authorization", "Bearer " + token)
                    .build();

            // Usamos exchange porque es el método más flexible para manejar Headers y EntityBody
            ResponseEntity<TareaDTO> response = restTemplate.exchange(request, TareaDTO.class);

            return response.getBody() != null ? List.of(response.getBody()) : Collections.emptyList();
        }
    }
}