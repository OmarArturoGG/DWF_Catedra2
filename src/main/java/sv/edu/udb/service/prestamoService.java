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
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaLimite(LocalDateTime.now().plusDays(diasPrestamo));
        prestamo.setEstado("ACTIVO");
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamosActivosPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuarioAndEstado(usuario, "ACTIVO");
    }

    public List<Prestamo> listarTodosLosPrestamosPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario);
    }
}