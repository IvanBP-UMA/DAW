package songlist.services;

import org.springframework.stereotype.Service;
import songlist.DTO.CancionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CancionService {
    List<CancionDTO> listaCanciones = new ArrayList<>();
    Long idCounter = 1L;

    public List<CancionDTO> getCanciones(){
        return listaCanciones;
    }

    public CancionDTO create(CancionDTO cancion){
        cancion.setId(idCounter);
        idCounter++;
        listaCanciones.add(cancion);
        return cancion;
    }

    public Optional<CancionDTO> getById(Long id){
        return listaCanciones.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Optional<CancionDTO> updateCancion(Long id, CancionDTO cancion){
        Optional<CancionDTO> result = getById(id);
        if (result.isPresent()){
            CancionDTO actualizada = result.get();
            actualizada.setTitulo(cancion.getTitulo());
            actualizada.setCantante(cancion.getCantante());
            actualizada.setAnio(cancion.getAnio());
        }
        return result;
    }

    public boolean delete(Long id){
        return listaCanciones.removeIf(c -> c.getId().equals(id));
    }

    public List<CancionDTO> filterByCAntante(String cantante){
        return listaCanciones.stream().filter(c -> c.getCantante().equals(cantante)).toList();
    }
}
