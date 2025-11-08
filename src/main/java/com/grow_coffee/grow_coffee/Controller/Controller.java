package com.grow_coffee.grow_coffee.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
@RequestMapping("/api/usuarios")
public class Controller {

    @Autowired
    private UsuarioService usuarioService;

    //Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errores = result.getAllErrors()
                                         .stream()
                                         .map(ObjectError::getDefaultMessage)
                                         .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            nuevoUsuario.setClave(null); // no devolver la contraseña
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String clave = loginData.get("clave");
        String rol = loginData.get("rol");

        if (correo == null || clave == null) {
            return ResponseEntity.badRequest().body("Correo y clave son requeridos");
        }

        Optional<Usuario> userOpt = usuarioService.obtenerPorCorreo(correo);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario user = userOpt.get();

        if (!usuarioService.verificarClave(clave, user.getClave())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        if (rol != null && user.getRol() != null && !user.getRol().toString().equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Rol incorrecto");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("correo", user.getCorreo());
        response.put("nombre", user.getNombre());
        response.put("rol", user.getRol());
        response.put("mensaje", "Login exitoso");

        return ResponseEntity.ok(response);
    }
}
