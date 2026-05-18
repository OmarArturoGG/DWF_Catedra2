package sv.edu.udb.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "portada_url")
    private String portadaUrl;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(length = 2000)
    private String descripcion;

    private String isbn;

    private String editorial;

    @Column(name = "preview_link")
    private String previewLink;

    private Integer paginas;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestamo> prestamos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ListaDeseos> listaDeseos = new ArrayList<>();

}
