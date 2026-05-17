package sv.edu.udb.repository;

import sv.edu.udb.entity.Prestamo;
import sv.edu.udb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface prestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUsuarioAndEstado(Usuario usuario, String estado);
    List<Prestamo> findByUsuario(Usuario usuario);
}
