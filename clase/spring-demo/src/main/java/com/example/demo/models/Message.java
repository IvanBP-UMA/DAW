package com.example.demo.models;

public class Message {
    private  String nombre;
    private int edad;
    private String ip;

    public int getEdad() {
        return edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
