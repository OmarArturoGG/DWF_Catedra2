package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.edu.udb.entity.Autor;

@Repository
public interface autorRepository extends JpaRepository<Autor, Long> {
}
