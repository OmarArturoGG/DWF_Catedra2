package sv.edu.udb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.service.usuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.registrarUsuario(usuario.getNombre(), usuario.getEmail(), usuario.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password) {
        if (!usuarioService.validarLogin(email, password)) {
            throw new BusinessException("Email o contraseña incorrectos");
        }
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", true);
        respuesta.put("usuarioId", usuario.getId());
        respuesta.put("usuarioNombre", usuario.getNombre());
        return ResponseEntity.ok(respuesta);
    }
}
