package es.uma.informatica.daw.tutorialtesting.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uma.informatica.daw.tutorialtesting.entidades.Proyecto;

@Repository
public interface ProyectoRepo extends JpaRepository<Proyecto, Long> {

}
