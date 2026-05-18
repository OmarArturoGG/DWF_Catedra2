package sv.edu.udb.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.entity.*;
import sv.edu.udb.repository.libroRepository;
import sv.edu.udb.repository.listaDeseosRepository;
import sv.edu.udb.repository.prestamoRepository;
import sv.edu.udb.repository.usuarioRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PrestamoYListaDeseosIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private usuarioRepository usuarioRepository;
    @Autowired
    private libroRepository libroRepository;
    @Autowired
    private prestamoRepository prestamoRepository;
    @Autowired
    private listaDeseosRepository listaDeseosRepository;

    private Usuario usuario;
    private Libro libro;
    private Prestamo prestamo;
    private ListaDeseos deseo;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setEmail("test@udb.edu.sv");
        usuario.setPassword("hash");
        usuario.setRol(Rol.voluntario);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario = usuarioRepository.save(usuario);

        libro = new Libro();
        libro.setTitulo("Libro Test");
        libro.setAutor("Autor Test");
        libro.setTipo("FISICO");
        libro.setDisponible(false);
        libro = libroRepository.save(libro);

        prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setEstado("ACTIVO");
        prestamo.setFechaPrestamo(LocalDateTime.of(2026, 5, 1, 9, 0));
        prestamo.setFechaLimite(LocalDateTime.of(2026, 5, 16, 9, 0));
        prestamo = prestamoRepository.save(prestamo);

        Libro libroDeseo = new Libro();
        libroDeseo.setTitulo("Libro Deseo");
        libroDeseo.setAutor("Autor Deseo");
        libroDeseo.setTipo("DIGITAL");
        libroDeseo.setDisponible(true);
        libroDeseo = libroRepository.save(libroDeseo);

        deseo = new ListaDeseos();
        deseo.setUsuario(usuario);
        deseo.setLibro(libroDeseo);
        deseo.setFechaAgregado(LocalDateTime.now());
        deseo = listaDeseosRepository.save(deseo);
    }

    @Test
    void putCondicionesPrestamo_actualizaDiasEnBaseDeDatos() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("diasPermitidos", 10);

        mockMvc.perform(put("/api/prestamos/{id}/condiciones", prestamo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        Prestamo recargado = prestamoRepository.findById(prestamo.getId()).orElseThrow();
        assertEquals(LocalDateTime.of(2026, 5, 11, 9, 0), recargado.getFechaLimite());
    }

    @Test
    void deleteListaDeseos_eliminaRegistroSinDejarHuerfanos() throws Exception {
        Long usuarioId = usuario.getId();
        Long libroDeseoId = deseo.getLibro().getId();

        mockMvc.perform(delete("/api/listadeseos/usuario/{usuarioId}/libro/{libroId}", usuarioId, libroDeseoId))
                .andExpect(status().isNoContent());

        assertTrue(listaDeseosRepository.findByUsuarioAndLibro(usuario, deseo.getLibro()).isEmpty());
        assertTrue(usuarioRepository.findById(usuarioId).isPresent());
        assertTrue(libroRepository.findById(libroDeseoId).isPresent());
        assertFalse(prestamoRepository.findByUsuarioId(usuarioId).isEmpty());
    }
}
