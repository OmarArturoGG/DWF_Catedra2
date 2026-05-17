package sv.edu.udb.controller;

import sv.edu.udb.entity.ListaDeseos;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.service.listaDeseosService;
import sv.edu.udb.service.usuarioService;
import sv.edu.udb.service.libroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/listadeseos")
public class listaDeseosController {

    @Autowired
    private listaDeseosService listaDeseosService;

    @Autowired
    private usuarioService usuarioService;

    @Autowired
    private libroService libroService;

    @PostMapping("/agregar")
    public Map<String, Object> agregarAListaDeseos(@RequestParam Long usuarioId,
                                                   @RequestParam Long libroId) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Usuario usuario = usuarioService.buscarPorId(usuarioId).orElse(null);
            Libro libro = libroService.buscarPorId(libroId).orElse(null);

            if (usuario == null || libro == null) {
                respuesta.put("success", false);
                respuesta.put("mensaje", "Usuario o libro no encontrado");
                return respuesta;
            }

            ListaDeseos listaDeseos = listaDeseosService.agregarAListaDeseos(usuario, libro);
            respuesta.put("success", true);
            respuesta.put("mensaje", "Libro agregado a lista de deseos");
            respuesta.put("listaDeseos", listaDeseos);
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("mensaje", "Error: " + e.getMessage());
        }
        return respuesta;
    }

    @GetMapping("/{usuarioId}")
    public List<ListaDeseos> verListaDeseos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId).orElse(null);
        if (usuario == null) return null;
        return listaDeseosService.listarListaDeseosPorUsuario(usuario);
    }
}