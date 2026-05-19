package sv.edu.udb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PrestamoRequest {

    @NotNull(message = "usuarioId es obligatorio")
    private Long usuarioId;

    @NotNull(message = "libroId es obligatorio")
    private Long libroId;

    @Min(value = 1, message = "Los dias de prestamo deben ser mayores a cero")
    private int diasPrestamo;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    public void setDiasPrestamo(int diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }
}
