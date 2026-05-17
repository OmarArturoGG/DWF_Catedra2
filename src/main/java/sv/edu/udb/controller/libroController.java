package sv.edu.udb.controller;

import sv.edu.udb.entity.Libro;
import sv.edu.udb.service.libroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class libroController {

    @Autowired
    private libroService libroService;

    @GetMapping
    public List<Libro> listarTodos() {
        return libroService.listarTodosLosLibros();
    }

    @GetMapping("/disponibles")
    public List<Libro> listarDisponibles() {
        return libroService.listarLibrosDisponibles();
    }

    @GetMapping("/{id}")
    public Libro buscarPorId(@PathVariable Long id) {
        return libroService.buscarPorId(id).orElse(null);
    }
}
