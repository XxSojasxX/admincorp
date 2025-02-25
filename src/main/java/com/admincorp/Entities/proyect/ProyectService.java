package com.admincorp.Entities.proyect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admincorp.Login.User.Users;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Service
public class ProyectService {

    @Autowired
    private ProyectRepository proyectRepository;

    // Insert
    public Proyect proyectoSave(Proyect entity) {
        // Verificar que el líder no sea nulo
        if (entity.getLeader() == null || entity.getLeader().getId() == null) {
            throw new IllegalArgumentException("Líder no puede ser nulo");
        }

        return proyectRepository.save(entity);
    }

    // Select
    public Proyect proyectoFindById(Long id) {
        Proyect proyect = proyectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        if (proyect.isDeleted()) {
            throw new EntityNotFoundException("Proyecto no encontrado");
        }

        return proyect;
    }

    // Select All
    public List<Proyect> proyectoFindAll() {
        Iterable<Proyect> iterable = proyectRepository.findAllByDeletedFalse();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Update
    public Proyect proyectoUpdate(Long id, Proyect updatedProyect) {
        Proyect existingProyect = proyectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        if (updatedProyect.getTitulo() != null) {
            existingProyect.setTitulo(updatedProyect.getTitulo());
        }
        if (updatedProyect.getDescripcion() != null) {
            existingProyect.setDescripcion(updatedProyect.getDescripcion());
        }
        if (updatedProyect.getEstado() != null) {
            existingProyect.setEstado(updatedProyect.getEstado());
        }
        if (updatedProyect.getLeader() != null) {
            existingProyect.setLeader(updatedProyect.getLeader());
        }

        return proyectRepository.save(existingProyect);
    }

    // Delete (Logical)
    public void proyectoDeleteById(Long id) {
        Proyect proyect = proyectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        proyect.setDeleted(true);
        proyect.setDeleteAt(LocalDateTime.now());
        proyectRepository.save(proyect);
    }
}