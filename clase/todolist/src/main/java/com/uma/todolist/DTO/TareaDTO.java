package com.uma.todolist.DTO;

import com.uma.todolist.models.Tarea;

public class TareaDTO {
    private long id;
    private String titulo;
    private boolean completada;

    public TareaDTO(long id, String titulo, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.completada = completada;
    }

    public TareaDTO(Tarea t){
        this.id = t.getId();
        this.titulo = t.getTitulo();
        this.completada = t.isCompletada();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
