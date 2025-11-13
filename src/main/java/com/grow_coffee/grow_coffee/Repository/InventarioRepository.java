package com.grow_coffee.grow_coffee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grow_coffee.grow_coffee.Model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
