package sv.edu.udb.repository;

import sv.edu.udb.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface libroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByDisponibleTrue();
    List<Libro> findByTipo(String tipo);
}