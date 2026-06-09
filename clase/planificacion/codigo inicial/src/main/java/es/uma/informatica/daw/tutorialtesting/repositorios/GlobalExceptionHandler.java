package es.uma.informatica.daw.tutorialtesting.repositorios;

import es.uma.informatica.daw.tutorialtesting.excepciones.ProyectoNoEncontrado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProyectoNoEncontrado.class)
    public ResponseEntity<Void> manejarProyectoNoEncontrado(ProyectoNoEncontrado ex) {
        return ResponseEntity.notFound().build();
    }
}