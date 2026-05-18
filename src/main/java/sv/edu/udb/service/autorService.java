package sv.edu.udb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.entity.Autor;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.repository.autorRepository;

import java.util.List;

@Service
public class autorService {

    @Autowired
    private autorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor obtenerPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id " + id));
    }

    public Autor crear(Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del autor es obligatorio");
        }
        return autorRepository.save(autor);
    }

    public Autor actualizar(Long id, Autor payload) {
        Autor autor = obtenerPorId(id);
        if (payload.getNombre() == null || payload.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del autor es obligatorio");
        }
        autor.setNombre(payload.getNombre());
        return autorRepository.save(autor);
    }

    public void eliminar(Long id) {
        Autor autor = obtenerPorId(id);
        autorRepository.delete(autor);
    }
}
