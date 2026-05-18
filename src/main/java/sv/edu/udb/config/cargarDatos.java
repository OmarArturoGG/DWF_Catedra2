package sv.edu.udb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.repository.libroRepository;
import sv.edu.udb.service.usuarioService; // Added import for usuarioService

import java.time.LocalDate;

@Component
public class cargarDatos implements CommandLineRunner {

    @Autowired
    private libroRepository libroRepository;

    @Autowired
    private usuarioService usuarioService; // Autowired usuarioService

    @Override
    public void run(String... args) throws Exception {

        // Check and create default users
        if (usuarioService.buscarPorEmail("admin@biblioteca.com").isEmpty()) {
            usuarioService.registrarUsuario("Admin", "admin@biblioteca.com", "admin123", "ADMIN");
            System.out.println("=== Admin user created: admin@biblioteca.com / admin123 ===");
        }

        if (usuarioService.buscarPorEmail("user@biblioteca.com").isEmpty()) {
            usuarioService.registrarUsuario("User", "user@biblioteca.com", "user123", "VOLUNTARIO"); // Using "VOLUNTARIO" for the role
            System.out.println("=== Normal user created: user@biblioteca.com / user123 ===");
        }


        if (libroRepository.count() == 0) {
            // Initial books
            Libro libro1 = new Libro();
            libro1.setTitulo("Cien años de soledad");
            libro1.setAutor("Gabriel García Márquez");
            libro1.setFechaPublicacion(LocalDate.of(1967, 5, 30));
            libro1.setPortadaUrl("https://covers.openlibrary.org/b/id/12525344-L.jpg");
            libro1.setTipo("FISICO");
            libro1.setDisponible(true);
            libroRepository.save(libro1);

            Libro libro2 = new Libro();
            libro2.setTitulo("El amor en los tiempos del cólera");
            libro2.setAutor("Gabriel García Márquez");
            libro2.setFechaPublicacion(LocalDate.of(1985, 9, 15));
            libro2.setPortadaUrl("https://covers.openlibrary.org/b/id/12525345-L.jpg");
            libro2.setTipo("FISICO");
            libro2.setDisponible(true);
            libroRepository.save(libro2);

            // Virtual Book 1: Don Quijote
            Libro libro3 = new Libro();
            libro3.setTitulo("Don Quijote de la Mancha");
            libro3.setAutor("Miguel de Cervantes Saavedra");
            libro3.setFechaPublicacion(LocalDate.of(1605, 1, 16));
            libro3.setPortadaUrl("https://covers.openlibrary.org/b/id/8269780-L.jpg");
            libro3.setTipo("VIRTUAL");
            libro3.setPdfUrl("static/pdfs/Don-Quijote.pdf"); // URL del PDF
            libro3.setDisponible(true);
            libroRepository.save(libro3);

            // Virtual Book 2: El fabricante de ataúdes
            Libro libro4 = new Libro();
            libro4.setTitulo("El fabricante de ataúdes");
            libro4.setAutor("Alexander Pushkin");
            libro4.setFechaPublicacion(LocalDate.of(1831, 1, 1));
            libro4.setPortadaUrl("https://covers.openlibrary.org/b/id/10499710-L.jpg");
            libro4.setTipo("VIRTUAL");
            libro4.setPdfUrl("/pdfs/El_fabricante_de_ataudes.pdf"); // URL del PDF
            libro4.setDisponible(true);
            libroRepository.save(libro4);

            // Virtual Book 3: El fantasma de Canterville
            Libro libro5 = new Libro();
            libro5.setTitulo("El fantasma de Canterville");
            libro5.setAutor("Oscar Wilde");
            libro5.setFechaPublicacion(LocalDate.of(1887, 1, 1));
            libro5.setPortadaUrl("https://covers.openlibrary.org/b/id/8816401-L.jpg");
            libro5.setTipo("VIRTUAL");
            libro5.setPdfUrl("/pdfs/El_fantasma_de_Canterville.pdf"); // URL del PDF
            libro5.setDisponible(true);
            libroRepository.save(libro5);

            // Virtual Book 4: El jugador
            Libro libro6 = new Libro();
            libro6.setTitulo("El jugador");
            libro6.setAutor("Fyodor Dostoevsky");
            libro6.setFechaPublicacion(LocalDate.of(1866, 1, 1));
            libro6.setPortadaUrl("https://covers.openlibrary.org/b/id/10008892-L.jpg");
            libro6.setTipo("VIRTUAL");
            libro6.setPdfUrl("/pdfs/El_jugador.pdf"); // URL del PDF
            libro6.setDisponible(true);
            libroRepository.save(libro6);

            // Virtual Book 5: El principito
            Libro libro7 = new Libro();
            libro7.setTitulo("El principito");
            libro7.setAutor("Antoine de Saint-Exupéry");
            libro7.setFechaPublicacion(LocalDate.of(1943, 4, 6));
            libro7.setPortadaUrl("https://covers.openlibrary.org/b/id/10667083-L.jpg");
            libro7.setTipo("VIRTUAL");
            libro7.setPdfUrl("/pdfs/El_principito.pdf"); // URL del PDF
            libro7.setDisponible(true);
            libroRepository.save(libro7);

            Libro libro8 = new Libro();
            libro8.setTitulo("Ficciones");
            libro8.setAutor("Jorge Luis Borges");
            libro8.setFechaPublicacion(LocalDate.of(1944, 1, 1));
            libro8.setPortadaUrl("https://covers.openlibrary.org/b/id/12525351-L.jpg");
            libro8.setTipo("FISICO");
            libro8.setDisponible(true);
            libroRepository.save(libro8);

            Libro libro9 = new Libro();
            libro9.setTitulo("Pedro Páramo");
            libro9.setAutor("Juan Rulfo");
            libro9.setFechaPublicacion(LocalDate.of(1955, 1, 1));
            libro9.setPortadaUrl("https://covers.openlibrary.org/b/id/12525352-L.jpg");
            libro9.setTipo("FISICO");
            libro9.setDisponible(true);
            libroRepository.save(libro9);

            Libro libro10 = new Libro();
            libro10.setTitulo("La tregua");
            libro10.setAutor("Mario Benedetti");
            libro10.setFechaPublicacion(LocalDate.of(1960, 1, 1));
            libro10.setPortadaUrl("https://covers.openlibrary.org/b/id/12525353-L.jpg");
            libro10.setTipo("FISICO");
            libro10.setDisponible(true);
            libroRepository.save(libro10);

            Libro libro11 = new Libro();
            libro11.setTitulo("El túnel");
            libro11.setAutor("Ernesto Sábato");
            libro11.setFechaPublicacion(LocalDate.of(1948, 1, 1));
            libro11.setPortadaUrl("https://covers.openlibrary.org/b/id/12525354-L.jpg");
            libro11.setTipo("FISICO");
            libro11.setDisponible(true);
            libroRepository.save(libro11);

            Libro libro12 = new Libro();
            libro12.setTitulo("La ciudad y los perros");
            libro12.setAutor("Mario Vargas Llosa");
            libro12.setFechaPublicacion(LocalDate.of(1963, 1, 1));
            libro12.setPortadaUrl("https://covers.openlibrary.org/b/id/12525355-L.jpg");
            libro12.setTipo("FISICO");
            libro12.setDisponible(true);
            libroRepository.save(libro12);



            System.out.println("=== SE CARGARON LIBROS FÍSICOS Y VIRTUALES CON TUS PDFS ===");
        }
    }
}