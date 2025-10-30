package com.grow_coffee.grow_coffee.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grow_coffee.grow_coffee.Model.Usuario;
import com.grow_coffee.grow_coffee.Services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

@Autowired
private UsuarioService usuarioService;

// Redirigir a controladores de login y registro
@PostMapping("/login")
public String login(@RequestBody Usuario usuario) {
    Usuario user = usuarioService.login(usuario.getCorreo(), usuario.getClave());
    if (user != null) {
        return "✅ Login exitoso. Bienvenido " + user.getNombre();
    }
    return "❌ Credenciales incorrectas";
}


@PostMapping("/registro")
public Usuario registrar(@RequestBody Usuario usuario) {
    return usuarioService.registrarUsuario(usuario);
    }
}