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

        if(libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()){
            throw new RuntimeException("El título del libro está vacío");
        }

        if(libro.getAutor() == null || libro.getAutor().trim().isEmpty()){
            throw new RuntimeException("El autor está vacío");
        }

        if(libro.getTipo() == null || libro.getTipo().trim().isEmpty()){
            throw new RuntimeException("El tipo de libro está vacío");
        }

        if(libro.getDescripcion() == null || libro.getDescripcion().trim().isEmpty()){
            throw new RuntimeException("La descripción está vacía");
        }

        if(libro.getImagenUrl() == null || libro.getImagenUrl().trim().isEmpty()){
            throw new RuntimeException("La imagen del libro está vacía");
        }

        return libroRepository.save(libro);
    }
}