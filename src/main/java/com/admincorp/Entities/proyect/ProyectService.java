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
        Optional<Proyect> proyect = proyectRepository.findById(id);
        if (proyect.isPresent() && !proyect.get().isDeleted()) {
            return proyect.get();
        } else {
            throw new EntityNotFoundException("Proyecto con id " + id + " no encontrado");
        }
    }

    // Select All
    public List<Proyect> proyectoFindAll() {
        return proyectRepository.findAllByDeletedFalse();
    }

    // Update
    public Proyect proyectoUpdate(Long id, Proyect updatedProyect) {
        Proyect existingProyect = proyectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        existingProyect.setTitulo(updatedProyect.getTitulo());
        existingProyect.setDescripcion(updatedProyect.getDescripcion());
        existingProyect.setEstado(updatedProyect.getEstado());

        return proyectRepository.save(existingProyect);
    }

    // Delete
    public void proyectoDeleteById(Long id) {
        Proyect proyect = proyectoFindById(id);
        proyect.setDeleted(true);
        proyect.setDeleteAt(LocalDateTime.now());
        proyectRepository.save(proyect);
    }
}