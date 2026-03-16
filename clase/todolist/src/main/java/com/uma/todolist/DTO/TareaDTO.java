package com.uma.todolist.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.uma.todolist.models.Tarea;

public class TareaDTO {
    private Long id;
    private String titulo;
    private boolean completada;

    @JsonCreator
    public TareaDTO(Long id, String titulo, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.completada = completada;
    }

    public TareaDTO(Tarea t){
        this.id = t.getId();
        this.titulo = t.getTitulo();
        this.completada = t.isCompletada();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
