package sv.edu.udb.service;

import sv.edu.udb.entity.Prestamo;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.repository.prestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class prestamoService {

    @Autowired
    private prestamoRepository prestamoRepository;

    public Prestamo prestarLibro(Usuario usuario, Libro libro, int diasPrestamo) {

        if(usuario == null){
            throw new RuntimeException("El usuario no existe");
        }

        if(libro == null){
            throw new RuntimeException("El libro no existe");
        }

        if(!libro.isDisponible()){
            throw new RuntimeException("El libro no está disponible");
        }

        if(diasPrestamo <= 0){
            throw new RuntimeException("Los días de préstamo son inválidos");
        }

        Prestamo prestamo = new Prestamo();

        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);

        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaLimite(LocalDateTime.now().plusDays(diasPrestamo));

        prestamo.setEstado("ACTIVO");

        libro.setDisponible(false);

        return prestamoRepository.save(prestamo);
    }
}