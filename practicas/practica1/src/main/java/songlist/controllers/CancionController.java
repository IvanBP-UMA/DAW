package songlist.controllers;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import songlist.DTO.CancionDTO;
import songlist.services.CancionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/canciones")
public class CancionController {
    @Autowired
    private CancionService cancionService;

    @GetMapping
    public ResponseEntity<List<CancionDTO>> listaCanciones(@RequestParam(name = "cantante", required = false) String cantante){
        List<CancionDTO> canciones;
        if (cantante == null || cantante.isBlank()) {
            canciones = cancionService.getCanciones();
        }else {
            canciones = cancionService.filterByCAntante(cantante);
        }
        return ResponseEntity.ok(canciones);
    }

    @PostMapping
    public ResponseEntity<CancionDTO> create(@RequestBody CancionDTO cancion,
                                             UriComponentsBuilder uriComponentsBuilder) {
        CancionDTO creada = cancionService.create(cancion);
        URI location = uriComponentsBuilder.path("/canciones/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancionDTO> getById(@PathVariable Long id){
        return cancionService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CancionDTO> update(@PathVariable Long id, @RequestBody CancionDTO cancion) {
        return cancionService.updateCancion(id, cancion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (cancionService.delete(id)){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
