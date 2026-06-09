package es.uma.informatica.daw.tutorialtesting.controladores;

import java.net.URI;
import java.util.List;

import es.uma.informatica.daw.tutorialtesting.dto.ProyectoDTO;
import es.uma.informatica.daw.tutorialtesting.entidades.Proyecto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import es.uma.informatica.daw.tutorialtesting.servicios.LogicaProyectos;

@RestController
@RequestMapping("/api/planificador/proyectos")
public class ControladorProyectos {
	private LogicaProyectos servicio;

	public ControladorProyectos(LogicaProyectos servicioProyectos) {
		servicio = servicioProyectos;
	}

	@GetMapping
	public ResponseEntity<List<ProyectoDTO>> listaDeProyectos() {
		return ResponseEntity.ok(
				servicio.getTodosProyectos().stream()
						.map(ProyectoDTO::fromProyecto) // Transformamos cada entidad en DTO
						.toList()
		);
	}

	@PostMapping
	public ResponseEntity<ProyectoDTO> anadirProyecto(@RequestBody ProyectoDTO proyectoDTO, UriComponentsBuilder builder) {
		// 1. Convertimos el DTO que llega del cliente a Entidad para el servicio
		Proyecto nuevoProyecto = servicio.anadirProyecto(proyectoDTO.toEntity());

		// 2. Construimos la URI del nuevo recurso
		URI uri = builder
				.path("/api/planificador/proyectos/{id}")
				.buildAndExpand(nuevoProyecto.getId())
				.toUri();

		// 3. Devolvemos 201 Created, la cabecera Location y el DTO en el cuerpo
		return ResponseEntity
				.created(uri)
				.body(ProyectoDTO.fromProyecto(nuevoProyecto));
	}

	@GetMapping("{id}")
	public ResponseEntity<ProyectoDTO> getProyecto(@PathVariable Long id) {
		return ResponseEntity.of(
				servicio.getProyectoPorId(id)
						.map(ProyectoDTO::fromProyecto) // Transformamos el contenido del Optional si existe
		);
	}

	@PutMapping("{id}")
	public ResponseEntity<ProyectoDTO> modificarProyecto(@PathVariable Long id, @RequestBody ProyectoDTO proyectoDTO) {
		// 1. Aseguramos que el ID de la URL sea el que se usa
		proyectoDTO.setId(id);

		// 2. Llamamos a la capa de servicio
		// El servicio lanza ProyectoNoEncontrado si no existe,
		// manejado por nuestro GlobalExceptionHandler (404).
		servicio.modificarProyecto(proyectoDTO.toEntity());

		// 3. Confirmamos el éxito con 204 No Content
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> eliminarProyecto(@PathVariable Long id) {
		// Si el id no existe, el servicio lanzará ProyectoNoEncontrado
		// y nuestro GlobalExceptionHandler lo convertirá en un 404.
		servicio.eliminarProyecto(id);

		// Retornamos 204 (No Content) porque el recurso ha sido eliminado
		// y no hay nada que devolver en el cuerpo.
		return ResponseEntity.noContent().build();
	}
}