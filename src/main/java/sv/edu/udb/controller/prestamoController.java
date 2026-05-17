package sv.edu.udb.controller;

import sv.edu.udb.entity.Prestamo;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.service.prestamoService;
import sv.edu.udb.service.usuarioService;
import sv.edu.udb.service.libroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
public class prestamoController {

    @Autowired
    private prestamoService prestamoService;

    @Autowired
    private usuarioService usuarioService;

    @Autowired
    private libroService libroService;

    @PostMapping("/prestar")
    public Map<String, Object> prestarLibro(@RequestParam Long usuarioId,
                                            @RequestParam Long libroId,
                                            @RequestParam int diasPrestamo) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Usuario usuario = usuarioService.buscarPorId(usuarioId).orElse(null);
            Libro libro = libroService.buscarPorId(libroId).orElse(null);

            if (usuario == null || libro == null) {
                respuesta.put("success", false);
                respuesta.put("mensaje", "Usuario o libro no encontrado");
                return respuesta;
            }

            Prestamo prestamo = prestamoService.prestarLibro(usuario, libro, diasPrestamo);
            respuesta.put("success", true);
            respuesta.put("mensaje", "Préstamo realizado con éxito");
            respuesta.put("prestamo", prestamo);
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("mensaje", "Error al prestar: " + e.getMessage());
        }
        return respuesta;
    }

    @GetMapping("/activos/{usuarioId}")
    public List<Prestamo> prestamosActivos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId).orElse(null);
        if (usuario == null) return null;
        return prestamoService.listarPrestamosActivosPorUsuario(usuario);
    }

    @GetMapping("/historial/{usuarioId}")
    public List<Prestamo> historialPrestamos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId).orElse(null);
        if (usuario == null) return null;
        return prestamoService.listarTodosLosPrestamosPorUsuario(usuario);
    }
}