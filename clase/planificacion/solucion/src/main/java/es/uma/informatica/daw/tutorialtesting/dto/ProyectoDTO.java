package es.uma.informatica.daw.tutorialtesting.dto;

import es.uma.informatica.daw.tutorialtesting.entidades.Proyecto;
import java.util.Date;

public class ProyectoDTO {

    private Long id;
    private String nombre;
    private Date fechaInicio;
    private Integer duracion;

    // Constructor vacío necesario para la deserialización de Jackson (JSON -> Objeto)
    public ProyectoDTO() {}

    public ProyectoDTO(Long id, String nombre, Date fechaInicio, Integer duracion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
    }

    // Método de utilidad para convertir de Entidad a DTO
    public static ProyectoDTO fromProyecto(Proyecto proyecto) {
        return new ProyectoDTO(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getFechaInicio(),
                proyecto.getDuracion()
        );
    }

    public Proyecto toEntity() {
        // Al convertir de DTO a Entidad, normalmente el ID es null
        // porque es la base de datos quien lo genera (en el POST).
        return new Proyecto(
                this.id,
                this.nombre,
                this.fechaInicio,
                this.duracion
        );
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }
}