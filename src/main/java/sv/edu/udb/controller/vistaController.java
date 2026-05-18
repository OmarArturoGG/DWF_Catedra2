package sv.edu.udb.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sv.edu.udb.dto.LibroRequest;
import sv.edu.udb.entity.Autor;
import sv.edu.udb.entity.Editorial;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.exception.DuplicateResourceException;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.service.autorService;
import sv.edu.udb.service.editorialService;
import sv.edu.udb.service.libroService;
import sv.edu.udb.service.listaDeseosService;
import sv.edu.udb.service.prestamoService;
import sv.edu.udb.service.usuarioService;
import java.time.LocalDate;

@Controller
public class vistaController {

    @Autowired
    private usuarioService usuarioService;
    @Autowired
    private libroService libroService;
    @Autowired
    private autorService autorService;
    @Autowired
    private editorialService editorialService;
    @Autowired
    private listaDeseosService listaDeseosService;
    @Autowired
    private prestamoService prestamoService;

    @Value("${app.admin.code}")
    private String adminCode;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("usuarioId") != null) {
            return "redirect:/menu";
        }
        return "login";
    }

    @GetMapping("/registro")
    public String registro(HttpSession session) {
        if (session.getAttribute("usuarioId") != null) {
            return "redirect:/menu";
        }
        return "registro";
    }

    @PostMapping("/registro")
    public String registroSubmit(@RequestParam String nombre,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam(defaultValue = "USER") String tipoUsuario,
                                 @RequestParam(required = false) String codigoAdmin,
                                 RedirectAttributes ra) {
        try {
            String rol = "voluntario";
            if ("admin".equalsIgnoreCase(tipoUsuario)) {
                if (codigoAdmin == null || !codigoAdmin.equals(adminCode)) {
                    throw new BusinessException("Codigo de administrador invalido");
                }
                rol = "admin";
            }
            usuarioService.registrarUsuario(nombre, email, password, rol);
            ra.addFlashAttribute("ok", "Cuenta creada. Ahora inicia sesion.");
            return "redirect:/login";
        } catch (BusinessException | DuplicateResourceException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/registro";
        }
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes ra) {
        if (!usuarioService.validarLogin(email, password)) {
            ra.addFlashAttribute("error", "Credenciales invalidas");
            return "redirect:/login";
        }
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNombre", usuario.getNombre());
        String rol = usuario.getRol() == null ? "USER" : usuario.getRol().toString();
        session.setAttribute("usuarioRol", rol);
        return "admin".equalsIgnoreCase(rol) ? "redirect:/admin" : "redirect:/usuario";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/menu")
    public String menu(HttpSession session, Model model) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        return isAdmin(session) ? "redirect:/admin" : "redirect:/usuario";
    }

    @GetMapping("/usuario")
    public String usuarioInicio(HttpSession session, Model model) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        if (isAdmin(session)) {
            return "redirect:/admin";
        }
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));
        model.addAttribute("usuarioRol", session.getAttribute("usuarioRol"));
        model.addAttribute("libros", libroService.listarTodosLosLibros());
        return "usuario";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session,
                        Model model,
                        @RequestParam(required = false) Long editLibroId,
                        @RequestParam(required = false) Long editAutorId,
                        @RequestParam(required = false) Long editEditorialId) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        if (!isAdmin(session)) {
            return "redirect:/menu";
        }
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));
        model.addAttribute("usuarioRol", session.getAttribute("usuarioRol"));
        model.addAttribute("libros", libroService.listarTodosLosLibros());
        model.addAttribute("autores", autorService.listarTodos());
        model.addAttribute("editoriales", editorialService.listarTodos());
        model.addAttribute("prestamos", prestamoService.listarTodos());
        if (editLibroId != null) {
            model.addAttribute("libroEdit", libroService.obtenerPorId(editLibroId));
        }
        if (editAutorId != null) {
            model.addAttribute("autorEdit", autorService.obtenerPorId(editAutorId));
        }
        if (editEditorialId != null) {
            model.addAttribute("editorialEdit", editorialService.obtenerPorId(editEditorialId));
        }
        return "admin";
    }

    @PostMapping("/admin/libros/editar")
    public String actualizarLibro(@RequestParam Long id,
                                  @RequestParam String titulo,
                                  @RequestParam String autor,
                                  @RequestParam(required = false) String fechaPublicacion,
                                  @RequestParam(required = false) String portadaUrl,
                                  @RequestParam String tipo,
                                  @RequestParam(required = false) String descripcion,
                                  @RequestParam(required = false) String isbn,
                                  @RequestParam(required = false) String editorial,
                                  @RequestParam(required = false) String previewLink,
                                  @RequestParam(required = false) String pdfUrl,
                                  @RequestParam(required = false) Integer paginas,
                                  @RequestParam(defaultValue = "false") boolean disponible,
                                  RedirectAttributes ra) {
        try {
            LibroRequest req = buildLibroRequest(titulo, autor, fechaPublicacion, portadaUrl, tipo, descripcion, isbn, editorial, previewLink, pdfUrl, paginas, disponible);
            libroService.actualizarLibro(id, req);
            ra.addFlashAttribute("ok", "Material actualizado");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/libros/crear")
    public String crearLibro(@RequestParam String titulo,
                             @RequestParam String autor,
                             @RequestParam(required = false) String fechaPublicacion,
                             @RequestParam(required = false) String portadaUrl,
                             @RequestParam String tipo,
                             @RequestParam(required = false) String descripcion,
                             @RequestParam(required = false) String isbn,
                             @RequestParam(required = false) String editorial,
                             @RequestParam(required = false) String previewLink,
                             @RequestParam(required = false) String pdfUrl,
                             @RequestParam(required = false) Integer paginas,
                             @RequestParam(defaultValue = "true") boolean disponible,
                             RedirectAttributes ra) {
        try {
            LibroRequest req = buildLibroRequest(titulo, autor, fechaPublicacion, portadaUrl, tipo, descripcion, isbn, editorial, previewLink, pdfUrl, paginas, disponible);
            libroService.guardarLibro(req);
            ra.addFlashAttribute("ok", "Material creado");
        } catch (BusinessException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/libros/{id}/eliminar")
    public String eliminarLibro(@PathVariable Long id, RedirectAttributes ra) {
        try {
            libroService.eliminarLibro(id);
            ra.addFlashAttribute("ok", "Material eliminado");
        } catch (ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/autores/editar")
    public String actualizarAutor(@RequestParam Long id, @RequestParam String nombre, RedirectAttributes ra) {
        try {
            Autor autor = new Autor();
            autor.setNombre(nombre);
            autorService.actualizar(id, autor);
            ra.addFlashAttribute("ok", "Autor actualizado");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/autores/{id}/eliminar")
    public String eliminarAutor(@PathVariable Long id, RedirectAttributes ra) {
        try {
            autorService.eliminar(id);
            ra.addFlashAttribute("ok", "Autor eliminado");
        } catch (ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/editoriales/editar")
    public String actualizarEditorial(@RequestParam Long id, @RequestParam String nombre, RedirectAttributes ra) {
        try {
            Editorial editorial = new Editorial();
            editorial.setNombre(nombre);
            editorialService.actualizar(id, editorial);
            ra.addFlashAttribute("ok", "Editorial actualizada");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/editoriales/{id}/eliminar")
    public String eliminarEditorial(@PathVariable Long id, RedirectAttributes ra) {
        try {
            editorialService.eliminar(id);
            ra.addFlashAttribute("ok", "Editorial eliminada");
        } catch (ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/mi-cuenta")
    public String miCuenta(HttpSession session, Model model) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));
        model.addAttribute("usuarioRol", session.getAttribute("usuarioRol"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("listaDeseos", listaDeseosService.listarListaDeseosPorUsuario(usuario));
        return "mi-cuenta";
    }

    @GetMapping("/catalogo")
    public String catalogo(HttpSession session, Model model) {
        return "redirect:/usuario";
    }

    @PostMapping("/catalogo/prestar/{libroId}")
    public String prestarLibro(@PathVariable Long libroId, HttpSession session, RedirectAttributes ra) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        try {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            Usuario usuario = usuarioService.obtenerPorId(usuarioId);
            Libro libro = libroService.obtenerPorId(libroId);
            prestamoService.solicitarPrestamo(usuario, libro, 15);
            ra.addFlashAttribute("ok", "Solicitud enviada. Queda en espera de aprobacion del admin.");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/usuario";
    }

    @PostMapping("/catalogo/deseos/{libroId}")
    public String agregarDeseos(@PathVariable Long libroId, HttpSession session, RedirectAttributes ra) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        try {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            Usuario usuario = usuarioService.obtenerPorId(usuarioId);
            Libro libro = libroService.obtenerPorId(libroId);
            listaDeseosService.agregarAListaDeseos(usuario, libro);
            ra.addFlashAttribute("ok", "Agregado a lista de deseos");
        } catch (BusinessException | DuplicateResourceException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/usuario";
    }

    @GetMapping("/mis-prestamos")
    public String misPrestamos(HttpSession session, Model model) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));
        model.addAttribute("usuarioRol", session.getAttribute("usuarioRol"));
        model.addAttribute("prestamos", prestamoService.listarTodosLosPrestamosPorUsuario(usuario));
        return "mis-prestamos";
    }

    @PostMapping("/mis-prestamos/{prestamoId}/solicitar-devolucion")
    public String solicitarDevolucion(@PathVariable Long prestamoId, HttpSession session, RedirectAttributes ra) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        try {
            prestamoService.solicitarDevolucion(prestamoId);
            ra.addFlashAttribute("ok", "Solicitud de devolucion enviada al administrador.");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/mis-prestamos";
    }

    @PostMapping("/admin/prestamos/{prestamoId}/aprobar")
    public String aprobarPrestamoAdmin(@PathVariable Long prestamoId, HttpSession session, RedirectAttributes ra) {
        if (!isLogged(session) || !isAdmin(session)) {
            return "redirect:/login";
        }
        try {
            prestamoService.aprobarPrestamo(prestamoId);
            ra.addFlashAttribute("ok", "Prestamo aprobado.");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/prestamos/{prestamoId}/rechazar")
    public String rechazarPrestamoAdmin(@PathVariable Long prestamoId, HttpSession session, RedirectAttributes ra) {
        if (!isLogged(session) || !isAdmin(session)) {
            return "redirect:/login";
        }
        try {
            prestamoService.rechazarPrestamo(prestamoId);
            ra.addFlashAttribute("ok", "Prestamo rechazado.");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/prestamos/{prestamoId}/confirmar-devolucion")
    public String confirmarDevolucionAdmin(@PathVariable Long prestamoId, HttpSession session, RedirectAttributes ra) {
        if (!isLogged(session) || !isAdmin(session)) {
            return "redirect:/login";
        }
        try {
            prestamoService.confirmarDevolucionAdmin(prestamoId);
            ra.addFlashAttribute("ok", "Devolucion confirmada.");
        } catch (BusinessException | ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/mi-cuenta/lista-deseos/{libroId}/eliminar")
    public String removerDeseo(@PathVariable Long libroId, HttpSession session, RedirectAttributes ra) {
        if (!isLogged(session)) {
            return "redirect:/login";
        }
        try {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            Usuario usuario = usuarioService.obtenerPorId(usuarioId);
            Libro libro = libroService.obtenerPorId(libroId);
            listaDeseosService.eliminarLibroDeUsuario(usuario, libro);
            ra.addFlashAttribute("ok", "Elemento removido");
        } catch (ResourceNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/mi-cuenta";
    }

    private boolean isLogged(HttpSession session) {
        return session.getAttribute("usuarioId") != null;
    }

    private boolean isAdmin(HttpSession session) {
        Object rol = session.getAttribute("usuarioRol");
        return rol != null && "admin".equalsIgnoreCase(rol.toString());
    }

    private LibroRequest buildLibroRequest(String titulo,
                                           String autor,
                                           String fechaPublicacion,
                                           String portadaUrl,
                                           String tipo,
                                           String descripcion,
                                           String isbn,
                                           String editorial,
                                           String previewLink,
                                           String pdfUrl,
                                           Integer paginas,
                                           boolean disponible) {
        LibroRequest req = new LibroRequest();
        req.setTitulo(titulo);
        req.setAutor(autor);
        req.setTipo(tipo);
        req.setPortadaUrl(portadaUrl);
        req.setDescripcion(descripcion);
        req.setIsbn(isbn);
        req.setEditorial(editorial);
        req.setPreviewLink(previewLink);
        req.setPdfUrl(pdfUrl);
        req.setPaginas(paginas);
        req.setDisponible(disponible);
        if (fechaPublicacion != null && !fechaPublicacion.isBlank()) {
            req.setFechaPublicacion(LocalDate.parse(fechaPublicacion));
        }
        return req;
    }
}
