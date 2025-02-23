package com.admincorp.Entities.proyect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admincorp.Login.User.Users;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("unused")
@Service
public class ProyectService {

    @Autowired
    private ProyectRepository proyectoRepository;

    // Insert
    public Proyect proyectoSave(Proyect entity) {
        // Verificar que el líder no sea nulo
        if (entity.getLeader() == null || entity.getLeader().getId() == null) {
            throw new IllegalArgumentException("Líder no puede ser nulo");
        }

        return proyectoRepository.save(entity);
    }

    // Select
    public Proyect proyectoFindById(Long id) {
        Proyect proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        return proyecto;
    }

    // Select All
    public List<Proyect> proyectoFindAll() {
        Iterable<Proyect> iterable = proyectoRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Update
    public Proyect proyectoUpdate(Long id, Proyect updatedProyect) {
        Proyect existingProyect = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        existingProyect.setTitulo(updatedProyect.getTitulo());
        existingProyect.setDescripcion(updatedProyect.getDescripcion());
        existingProyect.setEstado(updatedProyect.getEstado());

        return proyectoRepository.save(existingProyect);
    }

    // Delete
    public void proyectoDeleteById(Long id) {
        Proyect proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        proyectoRepository.deleteById(id);
    }
}