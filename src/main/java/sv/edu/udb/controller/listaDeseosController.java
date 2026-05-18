package sv.edu.udb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.entity.ListaDeseos;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.service.libroService;
import sv.edu.udb.service.listaDeseosService;
import sv.edu.udb.service.usuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/listadeseos")
public class listaDeseosController {

    @Autowired
    private listaDeseosService listaDeseosService;

    @Autowired
    private usuarioService usuarioService;

    @Autowired
    private libroService libroService;

    @GetMapping
    public ResponseEntity<List<ListaDeseos>> listarTodos() {
        return ResponseEntity.ok(listaDeseosService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDeseos> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(listaDeseosService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ListaDeseos> agregarAListaDeseos(@RequestParam Long usuarioId, @RequestParam Long libroId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        Libro libro = libroService.obtenerPorId(libroId);
        ListaDeseos listaDeseos = listaDeseosService.agregarAListaDeseos(usuario, libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(listaDeseos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ListaDeseos>> verListaDeseos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        return ResponseEntity.ok(listaDeseosService.listarListaDeseosPorUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        listaDeseosService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}/libro/{libroId}")
    public ResponseEntity<Void> eliminarLibroDeLista(@PathVariable Long usuarioId, @PathVariable Long libroId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        Libro libro = libroService.obtenerPorId(libroId);
        listaDeseosService.eliminarLibroDeUsuario(usuario, libro);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> limpiarLista(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        listaDeseosService.limpiarListaUsuario(usuario);
        return ResponseEntity.noContent().build();
    }
}
