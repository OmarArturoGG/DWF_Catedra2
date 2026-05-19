package sv.edu.udb.dto;

import jakarta.validation.constraints.Min;

public class ActualizarDiasPrestamoRequest {

    @Min(value = 1, message = "Los dias permitidos deben ser mayores a cero")
    private int diasPermitidos;

    public int getDiasPermitidos() {
        return diasPermitidos;
    }

    public void setDiasPermitidos(int diasPermitidos) {
        this.diasPermitidos = diasPermitidos;
    }
}
