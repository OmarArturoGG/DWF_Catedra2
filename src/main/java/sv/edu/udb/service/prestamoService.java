package sv.edu.udb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.entity.Prestamo;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.repository.libroRepository;
import sv.edu.udb.repository.prestamoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class prestamoService {

    @Autowired
    private prestamoRepository prestamoRepository;

    @Autowired
    private libroRepository libroRepository;

    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    public Prestamo obtenerPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con id " + id));
    }

    public Prestamo prestarLibro(Usuario usuario, Libro libro, int diasPrestamo) {
        if (usuario == null) {
            throw new BusinessException("El usuario no existe");
        }
        if (libro == null) {
            throw new BusinessException("El libro no existe");
        }
        if (!Boolean.TRUE.equals(libro.getDisponible())) {
            throw new BusinessException("El libro no está disponible");
        }
        if (diasPrestamo <= 0) {
            throw new BusinessException("Los días de préstamo son inválidos");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaLimite(LocalDateTime.now().plusDays(diasPrestamo));
        prestamo.setEstado("ACTIVO");

        libro.setDisponible(false);
        libroRepository.save(libro);
        return prestamoRepository.save(prestamo);
    }

    public Prestamo actualizarDiasPrestamo(Long prestamoId, int diasExtra) {
        if (diasExtra == 0) {
            throw new BusinessException("La modificación de días no puede ser cero");
        }
        Prestamo prestamo = obtenerPorId(prestamoId);
        if (!"ACTIVO".equalsIgnoreCase(prestamo.getEstado())) {
            throw new BusinessException("Solo se pueden modificar días en préstamos activos");
        }
        LocalDateTime nuevaFecha = prestamo.getFechaLimite().plusDays(diasExtra);
        if (!nuevaFecha.isAfter(prestamo.getFechaPrestamo())) {
            throw new BusinessException("La fecha límite resultante no es válida");
        }
        prestamo.setFechaLimite(nuevaFecha);
        return prestamoRepository.save(prestamo);
    }

    public Prestamo actualizarDiasPermitidos(Long prestamoId, int diasPermitidos) {
        Prestamo prestamo = obtenerPorId(prestamoId);
        if (!"ACTIVO".equalsIgnoreCase(prestamo.getEstado())) {
            throw new BusinessException("Solo se pueden modificar dias en prestamos activos");
        }
        if (diasPermitidos <= 0) {
            throw new BusinessException("Los dias permitidos deben ser mayores a cero");
        }
        prestamo.setFechaLimite(prestamo.getFechaPrestamo().plusDays(diasPermitidos));
        return prestamoRepository.save(prestamo);
    }

    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = obtenerPorId(prestamoId);
        if (!"ACTIVO".equalsIgnoreCase(prestamo.getEstado())) {
            throw new BusinessException("Solo un préstamo activo puede devolverse");
        }
        prestamo.setEstado("DEVUELTO");
        Libro libro = prestamo.getLibro();
        libro.setDisponible(true);
        libroRepository.save(libro);
        return prestamoRepository.save(prestamo);
    }

    public Prestamo actualizarPrestamo(Long id, Prestamo payload) {
        Prestamo prestamo = obtenerPorId(id);
        if (payload.getEstado() != null && !payload.getEstado().trim().isEmpty()) {
            prestamo.setEstado(payload.getEstado());
        }
        if (payload.getFechaLimite() != null) {
            prestamo.setFechaLimite(payload.getFechaLimite());
        }
        return prestamoRepository.save(prestamo);
    }

    public void eliminarPrestamo(Long id) {
        Prestamo prestamo = obtenerPorId(id);
        prestamoRepository.delete(prestamo);
    }

    public List<Prestamo> listarPrestamosActivosPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuarioAndEstado(usuario, "ACTIVO");
    }

    public List<Prestamo> listarTodosLosPrestamosPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario);
    }
}
