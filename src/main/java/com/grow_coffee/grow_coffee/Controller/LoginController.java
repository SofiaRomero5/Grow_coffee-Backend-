package com.grow_coffee.grow_coffee.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String clave = loginData.get("clave");

        // Validar que se recibieron los datos
        if (correo == null || correo.isBlank() || clave == null || clave.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("El correo y la clave son obligatorios");
        }

        Optional<Usuario> userOpt = usuarioService.obtenerPorCorreo(correo);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario user = userOpt.get();

        // Verificar la contraseña usando BCrypt
        if (!usuarioService.verificarClave(clave, user.getClave())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        // Respuesta exitosa sin mostrar la contraseña
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("nombre", user.getNombre());
        response.put("correo", user.getCorreo());
        response.put("rol", user.getRol());
        response.put("mensaje", "✅ Login exitoso. Bienvenido " + user.getNombre());

        return ResponseEntity.ok(response);
    }
}
