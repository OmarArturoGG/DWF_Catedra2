package sv.edu.udb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.exception.DuplicateResourceException;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.repository.usuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario registrarUsuario(String nombre, String email, String password) {
        return registrarUsuario(nombre, email, password, "USER");
    }

    public Usuario registrarUsuario(String nombre, String email, String password, String rol) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre esta vacio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException("El correo esta vacio");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new BusinessException("Correo invalido");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BusinessException("La contrasena esta vacia");
        }
        if (password.length() < 6) {
            throw new BusinessException("La contrasena debe tener minimo 6 caracteres");
        }
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
        if (usuarioExistente.isPresent()) {
            throw new DuplicateResourceException("El correo ya esta registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRol(("ADMIN".equalsIgnoreCase(rol)) ? "ADMIN" : "USER");
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + id));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean validarLogin(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    public Usuario actualizar(Long id, Usuario payload) {
        Usuario usuario = obtenerPorId(id);
        if (payload.getNombre() != null && !payload.getNombre().trim().isEmpty()) {
            usuario.setNombre(payload.getNombre());
        }
        if (payload.getEmail() != null && !payload.getEmail().trim().isEmpty()) {
            Optional<Usuario> maybe = usuarioRepository.findByEmail(payload.getEmail());
            if (maybe.isPresent() && !maybe.get().getId().equals(id)) {
                throw new DuplicateResourceException("El correo ya esta registrado");
            }
            usuario.setEmail(payload.getEmail());
        }
        if (payload.getPassword() != null && !payload.getPassword().trim().isEmpty()) {
            if (payload.getPassword().length() < 6) {
                throw new BusinessException("La contrasena debe tener minimo 6 caracteres");
            }
            usuario.setPassword(passwordEncoder.encode(payload.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }
}
