package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.edu.udb.entity.Editorial;

@Repository
public interface editorialRepository extends JpaRepository<Editorial, Long> {
}
