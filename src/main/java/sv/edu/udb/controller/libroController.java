package sv.edu.udb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.service.libroService;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class libroController {

    @Autowired
    private libroService libroService;

    @GetMapping
    public ResponseEntity<List<Libro>> listarTodos() {
        return ResponseEntity.ok(libroService.listarTodosLosLibros());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Libro>> listarDisponibles() {
        return ResponseEntity.ok(libroService.listarLibrosDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.guardarLibro(libro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.actualizarLibro(id, libro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }
}
