package com.uma.todolist.controllers;

import com.uma.todolist.DTO.TareaDTO;
import com.uma.todolist.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<TareaDTO> listarTareas(){
        return tareaService.listarTodos();
    }

    @PostMapping
    public TareaDTO add (@RequestBody TareaDTO tarea){
        return tareaService.crear(tarea.getTitulo());
    }

    @GetMapping("/buscar")
    public List<TareaDTO> filtar(@RequestParam String titulo){
        return tareaService.filtrar(titulo);
    }

    @GetMapping("/{id}")
    public TareaDTO getByID(@PathVariable long id){
        return tareaService.getByID(id);
    }

    @PutMapping("/{id}")
    public TareaDTO completar(@PathVariable long id){
        return tareaService.completar(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id){
        return tareaService.delete(id);
    }
}
