package sv.edu.udb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class vistaController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/prestamos")
    public String prestamos() {
        return "prestamos";
    }

    @GetMapping("/activos")
    public String activos() {
        return "activos";
    }

    @GetMapping("/lista-deseos")
    public String listaDeseos() {
        return "lista-deseos";
    }

    @GetMapping("/detalles-libro")
    public String detallesLibro() {
        return "detalles-libro";
    }

    @GetMapping("/mi-cuenta")
    public String miCuenta() {
        return "mi-cuenta";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }


    @GetMapping("/buscar-virtuales")
    public String buscarVirtuales() {
        return "buscar-virtuales";

    }

    @GetMapping("/leer-pdf")
    public String leerPdf() {
        return "leer-pdf";
    }

    @GetMapping("/leer-libro")
    public String leerLibro() {
        return "leer-libro";
    }

}
