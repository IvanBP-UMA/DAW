package com.uma.todolist.services;

import com.uma.todolist.DTO.TareaDTO;
import com.uma.todolist.models.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.TareaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TareaService {
    @Autowired
    private TareaRepository tareaRepository;

    public List<TareaDTO> listarTodos(){
        return tareaRepository.findAll().stream().map(TareaDTO::new).toList();
    }

    public TareaDTO getByID(Long id){
        return tareaRepository.
    }

    public List<TareaDTO> filtrar(String titulo){
        return listaDeTareas.stream()
                .filter((Tarea t) -> (t.getTitulo().toLowerCase().contains(titulo.toLowerCase())))
                .map(TareaDTO::new).toList();
    }

    public TareaDTO crear (String titulo) {
        Tarea nueva = new Tarea(idCounter++, titulo, false, "ALTA");
        listaDeTareas.add(nueva);
        return new TareaDTO(nueva);
    }

    public TareaDTO completar (Long id){
        Tarea completed = listaDeTareas.stream().filter((Tarea t) -> t.getId().equals(id)).findFirst().orElse(null);
        TareaDTO result;
        if (completed != null){
            completed.setCompletada(true);
            result = new TareaDTO(completed);
        }else{
            result = null;
        }
        return result;
    }

    public boolean delete (Long id){
        return listaDeTareas.removeIf((Tarea t) -> t.getId().equals(id));
    }
}
