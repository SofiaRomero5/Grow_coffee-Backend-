package com.grow_coffee.grow_coffee.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.grow_coffee.grow_coffee.Model.Usuario;
import com.grow_coffee.grow_coffee.Repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder claveEncoder; 

    // Registrar usuario
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setClave(claveEncoder.encode(usuario.getClave())); // Encriptar contraseña
        return usuarioRepository.save(usuario);
    }

    // Buscar usuario por correo
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    // Verificar contraseña
    public boolean verificarClave(String rawPassword, String encodedPassword) {
        return claveEncoder.matches(rawPassword, encodedPassword);
    }

    // Login
    public Usuario login(String correo, String clave) {
        Optional<Usuario> userOpt = obtenerPorCorreo(correo);

        if (userOpt.isPresent() && verificarClave(clave, userOpt.get().getClave())) {
            return userOpt.get();
        }

        return null; // login fallido
    }
}
