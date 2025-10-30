package com.grow_coffee.grow_coffee.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grow_coffee.grow_coffee.Model.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    List<Actividad> findByUsuarioId(Long usuarioId);

    List<Actividad> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha);
}
