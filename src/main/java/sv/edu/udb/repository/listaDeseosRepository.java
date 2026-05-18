package sv.edu.udb.repository;

import sv.edu.udb.entity.ListaDeseos;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface listaDeseosRepository extends JpaRepository<ListaDeseos, Long> {
    List<ListaDeseos> findByUsuario(Usuario usuario);
    boolean existsByUsuarioAndLibro(Usuario usuario, Libro libro);
    void deleteByUsuarioAndLibro(Usuario usuario, Libro libro);
    void deleteByUsuario(Usuario usuario);
}
