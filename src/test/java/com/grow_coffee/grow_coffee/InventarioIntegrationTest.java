package com.grow_coffee.grow_coffee;

import com.grow_coffee.grow_coffee.Model.Inventario;
import com.grow_coffee.grow_coffee.Repository.InventarioRepository;
import com.grow_coffee.grow_coffee.Services.InventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InventarioIntegrationTest {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Test
    void testGuardarYBuscarInventario() {
        Inventario nuevo = new Inventario();
        nuevo.setNombre("Café molido");
        nuevo.setCantidad(20);
        nuevo.setUnidad("kg");                          // obligatorio según tu columna
        nuevo.setFechaVencimiento(LocalDate.now().plusMonths(6)); // CORRECTO
        nuevo.setPrecio(15000.0);                       // si añadiste precio al modelo

        Inventario guardado = inventarioService.guardar(nuevo);
        assertNotNull(guardado.getId(), "El ID no debe ser nulo después de guardar");

        Inventario encontrado = inventarioRepository.findById(guardado.getId()).orElse(null);
        assertNotNull(encontrado, "El inventario debería encontrarse en la base de datos");
        assertEquals("Café molido", encontrado.getNombre());
        assertEquals("kg", encontrado.getUnidad());
    }
}

