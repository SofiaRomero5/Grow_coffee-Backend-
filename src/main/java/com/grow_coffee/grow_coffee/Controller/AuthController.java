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
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String clave = loginData.get("clave");

        // Validar que los campos no estén vacíos
        if (correo == null || correo.isBlank() || clave == null || clave.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("El correo y la clave son obligatorios");
        }

        Optional<Usuario> userOpt = usuarioService.obtenerPorCorreo(correo);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario user = userOpt.get();

        // Verificar la contraseña encriptada
        if (!usuarioService.verificarClave(clave, user.getClave())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        // Crear respuesta sin contraseña
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("nombre", user.getNombre());
        response.put("correo", user.getCorreo());
        response.put("rol", user.getRol());
        response.put("mensaje", "✅ Login exitoso. Bienvenido " + user.getNombre());

        return ResponseEntity.ok(response);
    }

    //Registro
    @PostMapping("/registro")
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
            // No devolver la contraseña
            nuevoUsuario.setClave(null);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al registrar el usuario: " + e.getMessage());
        }
    }
}
