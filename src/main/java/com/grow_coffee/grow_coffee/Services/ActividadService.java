package com.grow_coffee.grow_coffee.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grow_coffee.grow_coffee.Model.Actividad;
import com.grow_coffee.grow_coffee.Repository.ActividadRepository;
import com.grow_coffee.grow_coffee.Repository.UsuarioRepository;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // para asociar usuario

    public List<Actividad> listarTodas() {
        return actividadRepository.findAll();
    }

    public Optional<Actividad> obtenerPorId(Long id) {
        return actividadRepository.findById(id);
    }

    public List<Actividad> listarPorUsuario(Long usuarioId) {
        return actividadRepository.findByUsuarioId(usuarioId);
    }

    public List<Actividad> listarPorUsuarioYFecha(Long usuarioId, LocalDate fecha) {
        return actividadRepository.findByUsuarioIdAndFecha(usuarioId, fecha);
    }

    public Actividad crearActividad(Actividad actividad, Long usuarioId) {
        if (usuarioId != null) {
            usuarioRepository.findById(usuarioId).ifPresent(actividad::setUsuario);
        }
        return actividadRepository.save(actividad);
    }

    public Actividad actualizarActividad(Long id, Actividad datos) {
        return actividadRepository.findById(id).map(act -> {
            act.setNombre(datos.getNombre());
            act.setFecha(datos.getFecha());
            act.setHoraInicio(datos.getHoraInicio());
            act.setHoraFin(datos.getHoraFin());
            act.setObservaciones(datos.getObservaciones());
            return actividadRepository.save(act);
        }).orElse(null);
    }

    public boolean eliminarActividad(Long id) {
        if (actividadRepository.existsById(id)) {
            actividadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
