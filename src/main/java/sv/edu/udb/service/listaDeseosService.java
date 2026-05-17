package sv.edu.udb.service;

import sv.edu.udb.entity.ListaDeseos;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.repository.listaDeseosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class listaDeseosService {

    @Autowired
    private listaDeseosRepository listaDeseosRepository;

    public ListaDeseos agregarAListaDeseos(Usuario usuario, Libro libro) {
        ListaDeseos listaDeseos = new ListaDeseos();
        listaDeseos.setUsuario(usuario);
        listaDeseos.setLibro(libro);
        listaDeseos.setFechaAgregado(LocalDateTime.now());
        return listaDeseosRepository.save(listaDeseos);
    }

    public List<ListaDeseos> listarListaDeseosPorUsuario(Usuario usuario) {
        return listaDeseosRepository.findByUsuario(usuario);
    }
}
