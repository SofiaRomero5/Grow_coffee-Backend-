package com.grow_coffee.grow_coffee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grow_coffee.grow_coffee.Model.Usuario;
import com.grow_coffee.grow_coffee.Services.UsuarioService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public String login(@RequestBody Usuario usuario) {
        Usuario user = usuarioService.login(usuario.getCorreo(), usuario.getClave());

        if (user != null) {
            return "✅ Login exitoso. Bienvenido " + user.getNombre();
        } else {
            return "❌ Datos incorrectas";
        }
    }
}
