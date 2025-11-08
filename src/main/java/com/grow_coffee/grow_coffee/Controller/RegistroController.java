package com.grow_coffee.grow_coffee.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grow_coffee.grow_coffee.Model.Usuario;
import com.grow_coffee.grow_coffee.Services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario, BindingResult result) {
        // Validación de campos
        if (result.hasErrors()) {
            List<String> errores = result.getAllErrors()
                                         .stream()
                                         .map(ObjectError::getDefaultMessage)
                                         .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            // Registrar usuario a través del servicio
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);

            // No devolver la contraseña en la respuesta
            nuevoUsuario.setClave(null);

            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            // Manejo de errores (correo duplicado, etc.)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al registrar el usuario: " + e.getMessage());
        }
    }
}
