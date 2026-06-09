package es.uma.informatica.daw.tutorialtesting.servicios;

import java.util.List;
import java.util.Optional;

import es.uma.informatica.daw.tutorialtesting.entidades.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.daw.tutorialtesting.repositorios.ProyectoRepo;
import es.uma.informatica.daw.tutorialtesting.excepciones.ProyectoNoEncontrado;

@Service
@Transactional
public class LogicaProyectos {
	
	private ProyectoRepo repo;
	
	@Autowired
	public LogicaProyectos(ProyectoRepo repo) {
		this.repo=repo;
	}
	
	public List<Proyecto> getTodosProyectos() {
		return repo.findAll();
	}
	
	public Proyecto anadirProyecto(Proyecto proyecto) {
		proyecto.setId(null);
		return repo.save(proyecto);
	}
	
	public Optional<Proyecto> getProyectoPorId(Long id) {
		return repo.findById(id);
	}

	public void modificarProyecto(Proyecto proyecto) {
		if (repo.existsById(proyecto.getId())) {
			repo.save(proyecto);
		} else {
			throw new ProyectoNoEncontrado();
		}
	}
	
	public void eliminarProyecto(Long id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
		} else {
			throw new ProyectoNoEncontrado();
		}
	}
}
