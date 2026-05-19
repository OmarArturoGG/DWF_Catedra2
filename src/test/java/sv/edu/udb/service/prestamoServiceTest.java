package sv.edu.udb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.entity.Prestamo;
import sv.edu.udb.exception.BusinessException;
import sv.edu.udb.repository.libroRepository;
import sv.edu.udb.repository.prestamoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class prestamoServiceTest {

    @Mock
    private prestamoRepository prestamoRepository;
    @Mock
    private libroRepository libroRepository;

    @InjectMocks
    private prestamoService prestamoService;

    @Test
    void actualizarDiasPermitidos_actualizaFechaLimiteCuandoPrestamoActivo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setEstado("ACTIVO");
        prestamo.setFechaPrestamo(LocalDateTime.of(2026, 5, 1, 10, 0));
        prestamo.setFechaLimite(LocalDateTime.of(2026, 5, 16, 10, 0));

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(prestamoRepository.save(any(Prestamo.class))).thenAnswer(inv -> inv.getArgument(0));

        Prestamo actualizado = prestamoService.actualizarDiasPermitidos(1L, 10);

        assertEquals(LocalDateTime.of(2026, 5, 11, 10, 0), actualizado.getFechaLimite());
        verify(prestamoRepository).save(prestamo);
    }

    @Test
    void actualizarDiasPermitidos_lanzaErrorSiPrestamoNoActivo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(2L);
        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaPrestamo(LocalDateTime.now());

        when(prestamoRepository.findById(2L)).thenReturn(Optional.of(prestamo));

        assertThrows(BusinessException.class, () -> prestamoService.actualizarDiasPermitidos(2L, 7));
    }
}
