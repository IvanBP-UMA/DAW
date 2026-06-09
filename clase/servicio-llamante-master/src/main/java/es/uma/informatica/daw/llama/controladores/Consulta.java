package es.uma.informatica.daw.llama.controladores;

import es.uma.informatica.daw.llama.dtos.TareaDTO;
import es.uma.informatica.daw.llama.servicios.ServicioConsulta;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/consulta")
public class Consulta {

    private ServicioConsulta servicioConsulta;

    public Consulta(ServicioConsulta servicioConsulta) {
        this.servicioConsulta = servicioConsulta;
    }

    @GetMapping
    public List<TareaDTO> obtenerTareasConsultadas(@RequestParam(value = "id", required = false) Long id) {
        return servicioConsulta.obtenerTareasConsultadas(id);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public void notFound() {}
}
