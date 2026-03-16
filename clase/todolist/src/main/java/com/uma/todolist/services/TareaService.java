package com.uma.todolist.services;

import com.uma.todolist.DTO.TareaDTO;
import com.uma.todolist.models.Tarea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TareaService {
    private List<Tarea> listaDeTareas = new ArrayList<>();
    private long idCounter = 1L;

    public List<TareaDTO> listarTodos(){
        return listaDeTareas.stream().map(TareaDTO::new).toList();
    }

    public TareaDTO getByID(long id){
        return listaDeTareas.stream()
                .filter((Tarea t) -> t.getId() == id)
                .map(TareaDTO::new).findFirst().orElse(null);
    }

    public List<TareaDTO> filtrar(String titulo){
        return listaDeTareas.stream()
                .filter((Tarea t) -> (t.getTitulo().toLowerCase().contains(titulo.toLowerCase())))
                .map((TareaDTO::new)).toList();
    }

    public TareaDTO crear (String titulo) {
        Tarea nueva = new Tarea(idCounter++, titulo, false, "ALTA");
        listaDeTareas.add(nueva);
        return new TareaDTO(nueva);
    }

    public TareaDTO completar (long id){
        Tarea completed = listaDeTareas.stream().filter((Tarea t) -> t.getId() == id).findFirst().orElse(null);
        TareaDTO result;
        if (completed != null){
            completed.setCompletada(true);
            result = new TareaDTO(completed);
        }else{
            result = null;
        }
        return result;
    }

    public boolean delete (long id){
        return listaDeTareas.removeIf((Tarea t) -> t.getId() == id);
    }
}
