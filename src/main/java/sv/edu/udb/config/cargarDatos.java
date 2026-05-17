package sv.edu.udb.config;

import sv.edu.udb.entity.Libro;
import sv.edu.udb.repository.libroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class cargarDatos implements CommandLineRunner {

    @Autowired
    private libroRepository libroRepository;

    @Override
    public void run(String... args) throws Exception {

        if (libroRepository.count() == 0) {


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

            Libro libro3 = new Libro();
            libro3.setTitulo("La sombra del viento");
            libro3.setAutor("Carlos Ruiz Zafón");
            libro3.setFechaPublicacion(LocalDate.of(2001, 4, 15));
            libro3.setPortadaUrl("https://covers.openlibrary.org/b/id/12525346-L.jpg");
            libro3.setTipo("FISICO");
            libro3.setDisponible(true);
            libroRepository.save(libro3);

            Libro libro4 = new Libro();
            libro4.setTitulo("El juego del ángel");
            libro4.setAutor("Carlos Ruiz Zafón");
            libro4.setFechaPublicacion(LocalDate.of(2008, 4, 15));
            libro4.setPortadaUrl("https://covers.openlibrary.org/b/id/12525347-L.jpg");
            libro4.setTipo("FISICO");
            libro4.setDisponible(true);
            libroRepository.save(libro4);

            Libro libro5 = new Libro();
            libro5.setTitulo("La casa de los espíritus");
            libro5.setAutor("Isabel Allende");
            libro5.setFechaPublicacion(LocalDate.of(1982, 1, 1));
            libro5.setPortadaUrl("https://covers.openlibrary.org/b/id/12525348-L.jpg");
            libro5.setTipo("FISICO");
            libro5.setDisponible(true);
            libroRepository.save(libro5);

            Libro libro6 = new Libro();
            libro6.setTitulo("Como agua para chocolate");
            libro6.setAutor("Laura Esquivel");
            libro6.setFechaPublicacion(LocalDate.of(1989, 1, 1));
            libro6.setPortadaUrl("https://covers.openlibrary.org/b/id/12525349-L.jpg");
            libro6.setTipo("FISICO");
            libro6.setDisponible(true);
            libroRepository.save(libro6);

            Libro libro7 = new Libro();
            libro7.setTitulo("Rayuela");
            libro7.setAutor("Julio Cortázar");
            libro7.setFechaPublicacion(LocalDate.of(1963, 6, 28));
            libro7.setPortadaUrl("https://covers.openlibrary.org/b/id/12525350-L.jpg");
            libro7.setTipo("FISICO");
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