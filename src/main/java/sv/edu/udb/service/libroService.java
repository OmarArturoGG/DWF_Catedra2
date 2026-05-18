package sv.edu.udb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.dto.LibroRequest;
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

    public Libro guardarLibro(LibroRequest request) {
        validarLibroRequest(request);
        Libro libro = mapToEntity(request);
        if (libro.getDisponible() == null) {
            libro.setDisponible(true);
        }
        return libroRepository.save(libro);
    }

    public Libro actualizarLibro(Long id, LibroRequest payload) {
        validarLibroRequest(payload);
        Libro libro = obtenerPorId(id);
        libro.setTitulo(payload.getTitulo());
        libro.setAutor(payload.getAutor());
        libro.setPortadaUrl(payload.getPortadaUrl());
        libro.setFechaPublicacion(payload.getFechaPublicacion());
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
        validarLibroEntity(libro);
        return libroRepository.save(libro);
    }

    public void eliminarLibro(Long id) {
        Libro libro = obtenerPorId(id);
        libroRepository.delete(libro);
    }

    private void validarLibroRequest(LibroRequest request) {
        if (request.getTipo() == null || request.getTipo().trim().isEmpty()) {
            throw new BusinessException("El tipo de libro esta vacio");
        }
        if (request.getPaginas() != null && request.getPaginas() < 0) {
            throw new BusinessException("Las paginas no pueden ser negativas");
        }
    }

    private void validarLibroEntity(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new BusinessException("El titulo del libro esta vacio");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new BusinessException("El autor esta vacio");
        }
        if (libro.getTipo() == null || libro.getTipo().trim().isEmpty()) {
            throw new BusinessException("El tipo de libro esta vacio");
        }
        if (libro.getPaginas() != null && libro.getPaginas() < 0) {
            throw new BusinessException("Las paginas no pueden ser negativas");
        }
    }

    private Libro mapToEntity(LibroRequest request) {
        Libro libro = new Libro();
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setTipo(request.getTipo());
        libro.setPortadaUrl(request.getPortadaUrl());
        libro.setFechaPublicacion(request.getFechaPublicacion());
        libro.setDescripcion(request.getDescripcion());
        libro.setIsbn(request.getIsbn());
        libro.setEditorial(request.getEditorial());
        libro.setPreviewLink(request.getPreviewLink());
        libro.setPaginas(request.getPaginas());
        libro.setPdfUrl(request.getPdfUrl());
        libro.setDisponible(request.getDisponible());
        return libro;
    }
}
