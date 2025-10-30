package com.grow_coffee.grow_coffee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grow_coffee.grow_coffee.Model.Usuario;
import com.grow_coffee.grow_coffee.Services.UsuarioService;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

@Autowired
private UsuarioService usuarioService;

@PostMapping
public Usuario registrar(@RequestBody Usuario usuario) {
    return usuarioService.registrarUsuario(usuario);
}


}