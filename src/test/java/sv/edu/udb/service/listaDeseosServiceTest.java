package sv.edu.udb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.entity.Libro;
import sv.edu.udb.entity.ListaDeseos;
import sv.edu.udb.entity.Usuario;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.repository.listaDeseosRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class listaDeseosServiceTest {

    @Mock
    private listaDeseosRepository listaDeseosRepository;

    @InjectMocks
    private listaDeseosService listaDeseosService;

    @Test
    void eliminarLibroDeUsuario_eliminaItemCuandoExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Libro libro = new Libro();
        libro.setId(2L);
        ListaDeseos item = new ListaDeseos();
        item.setId(3L);
        item.setUsuario(usuario);
        item.setLibro(libro);

        when(listaDeseosRepository.findByUsuarioAndLibro(usuario, libro)).thenReturn(Optional.of(item));

        listaDeseosService.eliminarLibroDeUsuario(usuario, libro);

        verify(listaDeseosRepository).delete(item);
    }

    @Test
    void eliminarLibroDeUsuario_lanzaErrorSiNoExiste() {
        Usuario usuario = new Usuario();
        Libro libro = new Libro();

        when(listaDeseosRepository.findByUsuarioAndLibro(usuario, libro)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> listaDeseosService.eliminarLibroDeUsuario(usuario, libro));
    }
}
