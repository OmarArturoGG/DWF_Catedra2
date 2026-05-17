package sv.edu.udb.controller;

import sv.edu.udb.entity.Usuario;
import sv.edu.udb.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @PostMapping("/registrar")
    public Map<String, Object> registrar(@RequestParam String nombre,
                                         @RequestParam String email,
                                         @RequestParam String password) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Usuario usuario = usuarioService.registrarUsuario(nombre, email, password);
            respuesta.put("success", true);
            respuesta.put("mensaje", "Usuario registrado con éxito");
            respuesta.put("usuario", usuario);
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("mensaje", "Error al registrar: " + e.getMessage());
        }
        return respuesta;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String email,
                                     @RequestParam String password) {
        Map<String, Object> respuesta = new HashMap<>();
        if (usuarioService.validarLogin(email, password)) {
            respuesta.put("success", true);
            respuesta.put("mensaje", "Login exitoso");
            Usuario usuario = usuarioService.buscarPorEmail(email).get();
            respuesta.put("usuarioId", usuario.getId());
            respuesta.put("usuarioNombre", usuario.getNombre());
        } else {
            respuesta.put("success", false);
            respuesta.put("mensaje", "Email o contraseña incorrectos");
        }
        return respuesta;
    }

    @GetMapping("/obtener/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id).orElse(null);
    }
}
