package sv.edu.udb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.entity.Editorial;
import sv.edu.udb.service.editorialService;

import java.util.List;

@RestController
@RequestMapping("/api/editoriales")
public class editorialController {

    @Autowired
    private editorialService editorialService;

    @GetMapping
    public ResponseEntity<List<Editorial>> listarTodos() {
        return ResponseEntity.ok(editorialService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editorial> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(editorialService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Editorial> crear(@RequestBody Editorial editorial) {
        return ResponseEntity.status(HttpStatus.CREATED).body(editorialService.crear(editorial));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Editorial> actualizar(@PathVariable Long id, @RequestBody Editorial editorial) {
        return ResponseEntity.ok(editorialService.actualizar(id, editorial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        editorialService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
