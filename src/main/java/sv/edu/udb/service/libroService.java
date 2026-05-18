package sv.edu.udb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.repository.libroRepository;

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

    public Libro obtenerPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id " + id));
    }

    public Libro guardarLibro(Libro libro) {
        validarLibro(libro);
        if (libro.getDisponible() == null) {
            libro.setDisponible(true);
        }
        return libroRepository.save(libro);
    }

    public Libro actualizarLibro(Long id, Libro payload) {
        Libro libro = obtenerPorId(id);
        libro.setTitulo(payload.getTitulo());
        libro.setAutor(payload.getAutor());
        libro.setFechaPublicacion(payload.getFechaPublicacion());
        libro.setPortadaUrl(payload.getPortadaUrl());
        libro.setTipo(payload.getTipo());
        libro.setDescripcion(payload.getDescripcion());
        libro.setIsbn(payload.getIsbn());
        libro.setEditorial(payload.getEditorial());
        libro.setPreviewLink(payload.getPreviewLink());
        libro.setPaginas(payload.getPaginas());
        libro.setPdfUrl(payload.getPdfUrl());
        if (payload.getDisponible() != null) {
            libro.setDisponible(payload.getDisponible());
        }
        validarLibro(libro);
        return libroRepository.save(libro);
    }

    public void eliminarLibro(Long id) {
        Libro libro = obtenerPorId(id);
        libroRepository.delete(libro);
    }

    private void validarLibro(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new BusinessException("El título del libro está vacío");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new BusinessException("El autor está vacío");
        }
        if (libro.getTipo() == null || libro.getTipo().trim().isEmpty()) {
            throw new BusinessException("El tipo de libro está vacío");
        }
    }
}
