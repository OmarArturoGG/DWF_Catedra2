package sv.edu.udb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.entity.Autor;
import sv.edu.udb.service.autorService;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class autorController {

    @Autowired
    private autorService autorService;

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Autor> crear(@RequestBody Autor autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.crear(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizar(@PathVariable Long id, @RequestBody Autor autor) {
        return ResponseEntity.ok(autorService.actualizar(id, autor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
