package es.uma.informatica.daw.practicajpa.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="federacion")
public class Federacion {
    @Id
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "federacion")
    private Set<Pais> paises;

}
