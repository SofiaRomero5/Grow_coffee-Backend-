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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.grow_coffee.grow_coffee.Model.Usuario;
import com.grow_coffee.grow_coffee.Repository.UsuarioRepository;
import com.grow_coffee.grow_coffee.Services.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder claveEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testRegistrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("CoffeeTeam");
        usuario.setCorreo("test@coffee.com");
        usuario.setClave("12345");

        when(claveEncoder.encode(any(CharSequence.class))).thenReturn("claveEncriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.registrarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("CoffeeTeam", resultado.getNombre());
        assertEquals("test@coffee.com", resultado.getCorreo());
    }
}
