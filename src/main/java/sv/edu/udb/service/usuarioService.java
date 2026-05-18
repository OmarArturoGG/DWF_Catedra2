package sv.edu.udb.service;

import sv.edu.udb.entity.Usuario;
import sv.edu.udb.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class usuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    public Usuario registrarUsuario(String nombre, String email, String password) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RuntimeException("El nombre está vacío");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("El correo está vacío");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("Correo inválido");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("La contraseña está vacía");
        }
        if (password.length() < 6) {
            throw new RuntimeException("La contraseña debe tener mínimo 6 caracteres");
        }
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setFechaRegistro(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
