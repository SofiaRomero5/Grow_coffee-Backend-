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
@RequestMapping("/api/usuarios")
public class Controller {

    @Autowired
    private UsuarioService usuarioService;

    // 🔹 Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.registrarUsuario(usuario);
            // No devolvemos la contraseña en la respuesta
            nuevo.setClave(null);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // 🔹 Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String clave = loginData.get("clave");
        String rol = loginData.get("rol");

        // Buscar usuario por correo (ajustado a tu modelo)
        Optional<Usuario> userOpt = usuarioService.obtenerPorCorreo(correo);


        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario user = userOpt.get();

        // 🔹 Verifica la contraseña usando el servicio
        if (!usuarioService.verificarClave(clave, user.getClave())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        // 🔹 Verifica el rol (si aplica)
        if (rol != null && !user.getRol().toString().equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Rol incorrecto");
        }

        // 🔹 Crear respuesta sin contraseña
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("correo", user.getCorreo());
        response.put("nombre", user.getNombre());
        response.put("rol", user.getRol());
        response.put("mensaje", "Login exitoso");

        return ResponseEntity.ok(response);
    }
}
