package com.grow_coffee.grow_coffee.Controller;

import com.grow_coffee.grow_coffee.Model.Inventario;
import com.grow_coffee.grow_coffee.Services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> listar() {
        return inventarioService.listarTodos();
    }

    @PostMapping
    public Inventario guardar(@RequestBody Inventario inventario) {
        return inventarioService.guardar(inventario);
    }

    @GetMapping("/{id}")
    public Optional<Inventario> buscarPorId(@PathVariable Long id) {
        return inventarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Inventario actualizar(@PathVariable Long id, @RequestBody Inventario inventario) {
        return inventarioService.actualizar(id, inventario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
    }
}
