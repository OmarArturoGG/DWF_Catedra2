package sv.edu.udb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.entity.Editorial;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.repository.editorialRepository;

import java.util.List;

@Service
public class editorialService {

    @Autowired
    private editorialRepository editorialRepository;

    public List<Editorial> listarTodos() {
        return editorialRepository.findAll();
    }

    public Editorial obtenerPorId(Long id) {
        return editorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Editorial no encontrada con id " + id));
    }

    public Editorial crear(Editorial editorial) {
        if (editorial.getNombre() == null || editorial.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre de la editorial es obligatorio");
        }
        return editorialRepository.save(editorial);
    }

    public Editorial actualizar(Long id, Editorial payload) {
        Editorial editorial = obtenerPorId(id);
        if (payload.getNombre() == null || payload.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre de la editorial es obligatorio");
        }
        editorial.setNombre(payload.getNombre());
        return editorialRepository.save(editorial);
    }

    public void eliminar(Long id) {
        Editorial editorial = obtenerPorId(id);
        editorialRepository.delete(editorial);
    }
}
