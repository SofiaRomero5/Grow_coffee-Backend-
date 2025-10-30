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

    private final BCryptPasswordEncoder claveEncoder = new BCryptPasswordEncoder();

    // 🔹 Registrar usuario (guarda la clave encriptada)
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setClave(claveEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }

    // 🔹 Obtener usuario por correo (ajustado a tu modelo)
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    // 🔹 Validar login manual
    public Usuario login(String correo, String clave) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isPresent()) {
            Usuario user = usuarioOpt.get();

            // Compara la clave ingresada con la clave encriptada
            if (claveEncoder.matches(clave, user.getClave())) {
                return user; // ✅ Autenticación exitosa
            }
        }

        //  Si no coincide o no existe
        return null;
    }

    //  Verificar clave (usado en el controlador)
    public boolean verificarClave(String claveIngresada, String claveEncriptada) {
        return claveEncoder.matches(claveIngresada, claveEncriptada);
    }
}

