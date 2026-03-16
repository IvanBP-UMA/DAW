package com.uma.todolist.models;

public class Tarea {
    private long id;
    private String titulo;
    private boolean completada;
    private String prioridad;

    public Tarea(long id, String titulo, boolean completada, String prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.completada = completada;
        this.prioridad = prioridad;
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
