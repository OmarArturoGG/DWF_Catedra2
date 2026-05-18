package sv.edu.udb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.entity.ListaDeseos;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.exception.DuplicateResourceException;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.repository.listaDeseosRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class listaDeseosService {

    @Autowired
    private listaDeseosRepository listaDeseosRepository;

    public List<ListaDeseos> listarTodos() {
        return listaDeseosRepository.findAll();
    }

    public ListaDeseos obtenerPorId(Long id) {
        return listaDeseosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista de deseos no encontrada con id " + id));
    }

    public ListaDeseos agregarAListaDeseos(Usuario usuario, Libro libro) {
        if (listaDeseosRepository.existsByUsuarioAndLibro(usuario, libro)) {
            throw new DuplicateResourceException("El libro ya está en la lista de deseos del usuario");
        }
        ListaDeseos listaDeseos = new ListaDeseos();
        listaDeseos.setUsuario(usuario);
        listaDeseos.setLibro(libro);
        listaDeseos.setFechaAgregado(LocalDateTime.now());
        return listaDeseosRepository.save(listaDeseos);
    }

    public List<ListaDeseos> listarListaDeseosPorUsuario(Usuario usuario) {
        return listaDeseosRepository.findByUsuario(usuario);
    }

    public void eliminarItem(Long id) {
        ListaDeseos item = obtenerPorId(id);
        listaDeseosRepository.delete(item);
    }

    public void eliminarLibroDeUsuario(Usuario usuario, Libro libro) {
        listaDeseosRepository.deleteByUsuarioAndLibro(usuario, libro);
    }

    public void limpiarListaUsuario(Usuario usuario) {
        listaDeseosRepository.deleteByUsuario(usuario);
    }
}
