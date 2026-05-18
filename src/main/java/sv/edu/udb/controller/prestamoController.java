package sv.edu.udb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.entity.Prestamo;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.service.libroService;
import sv.edu.udb.service.prestamoService;
import sv.edu.udb.service.usuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class prestamoController {

    @Autowired
    private prestamoService prestamoService;

    @Autowired
    private usuarioService usuarioService;

    @Autowired
    private libroService libroService;

    @GetMapping
    public ResponseEntity<List<Prestamo>> listarTodos() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Prestamo> prestarLibro(@RequestParam Long usuarioId,
                                                 @RequestParam Long libroId,
                                                 @RequestParam int diasPrestamo) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        Libro libro = libroService.obtenerPorId(libroId);
        Prestamo prestamo = prestamoService.prestarLibro(usuario, libro, diasPrestamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        return ResponseEntity.ok(prestamoService.actualizarPrestamo(id, prestamo));
    }

    @PutMapping("/{id}/dias")
    public ResponseEntity<Prestamo> actualizarDias(@PathVariable Long id, @RequestParam int dias) {
        return ResponseEntity.ok(prestamoService.actualizarDiasPrestamo(id, dias));
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverLibro(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        prestamoService.eliminarPrestamo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos/{usuarioId}")
    public ResponseEntity<List<Prestamo>> prestamosActivos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        return ResponseEntity.ok(prestamoService.listarPrestamosActivosPorUsuario(usuario));
    }

    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<List<Prestamo>> historialPrestamos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        return ResponseEntity.ok(prestamoService.listarTodosLosPrestamosPorUsuario(usuario));
    }
}
