package com.grow_coffee.grow_coffee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grow_coffee.grow_coffee.Model.Usuario;
import com.grow_coffee.grow_coffee.Repository.UsuarioRepository;
import com.grow_coffee.grow_coffee.Services.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testRegistrarUsuario() {
        // Crear un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setNombre("CoffeeTeam");
        usuario.setCorreo("test@coffee.com");
        usuario.setClave("12345");

        // Simular el guardado en el repositorio usando any() para evitar errores de igualdad
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Llamar al método correcto en el service
        Usuario resultado = usuarioService.registrarUsuario(usuario);

        // Verificaciones
        assertNotNull(resultado, "El usuario no debería ser null");
        assertEquals("CoffeeTeam", resultado.getNombre(), "El nombre no coincide");
        assertEquals("test@coffee.com", resultado.getCorreo(), "El correo no coincide");
    }
}
