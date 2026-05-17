package sv.edu.udb.service;

import sv.edu.udb.entity.Libro;
import sv.edu.udb.repository.libroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class libroService {

    @Autowired
    private libroRepository libroRepository;

    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> listarLibrosDisponibles() {
        return libroRepository.findByDisponibleTrue();
    }

    public List<Libro> listarLibrosPorTipo(String tipo) {
        return libroRepository.findByTipo(tipo);
    }

    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }
}
