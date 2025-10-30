package com.grow_coffee.grow_coffee.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grow_coffee.grow_coffee.Model.Actividad;
import com.grow_coffee.grow_coffee.Services.ActividadService;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping
    public List<Actividad> listarTodas() {
        return actividadService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actividad> obtener(@PathVariable Long id) {
        Optional<Actividad> act = actividadService.obtenerPorId(id);
        return act.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // listar por usuario (ej: /api/actividades/usuario/1)
    @GetMapping("/usuario/{usuarioId}")
    public List<Actividad> listarPorUsuario(@PathVariable Long usuarioId) {
        return actividadService.listarPorUsuario(usuarioId);
    }

    // listar por usuario y fecha (ej: /api/actividades/usuario/1?fecha=2025-10-30)
    @GetMapping("/usuario/{usuarioId}/por-fecha")
    public List<Actividad> listarPorUsuarioYFecha(
            @PathVariable Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return actividadService.listarPorUsuarioYFecha(usuarioId, fecha);
    }

    // crear (envía JSON de Actividad; si deseas relacionar con usuario, manda "usuario": {"id": 1} o usa usuarioId param)
    @PostMapping
    public ResponseEntity<Actividad> crear(@RequestBody Actividad actividad,
                                           @RequestParam(required = false) Long usuarioId) {
        Actividad creada = actividadService.crearActividad(actividad, usuarioId);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> actualizar(@PathVariable Long id, @RequestBody Actividad datos) {
        Actividad actualizada = actividadService.actualizarActividad(id, datos);
        if (actualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean ok = actividadService.eliminarActividad(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
