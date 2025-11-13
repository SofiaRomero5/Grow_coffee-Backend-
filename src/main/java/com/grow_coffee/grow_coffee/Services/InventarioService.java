package com.grow_coffee.grow_coffee.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grow_coffee.grow_coffee.Model.Inventario;
import com.grow_coffee.grow_coffee.Repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> listarTodos() {
        return inventarioRepository.findAll();
    }

    public Inventario guardar(Inventario insumo) {
        return inventarioRepository.save(insumo);
    }

    public Optional<Inventario> buscarPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public Inventario actualizar(Long id, Inventario nuevoInsumo) {
        return inventarioRepository.findById(id).map(insumo -> {
            insumo.setNombre(nuevoInsumo.getNombre());
            insumo.setCantidad(nuevoInsumo.getCantidad());
            insumo.setUnidad(nuevoInsumo.getUnidad());
            insumo.setFechaVencimiento(nuevoInsumo.getFechaVencimiento());
            return inventarioRepository.save(insumo);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        inventarioRepository.deleteById(id);
    }
}
