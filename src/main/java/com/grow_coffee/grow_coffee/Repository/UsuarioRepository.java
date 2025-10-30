package com.grow_coffee.grow_coffee.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grow_coffee.grow_coffee.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}
