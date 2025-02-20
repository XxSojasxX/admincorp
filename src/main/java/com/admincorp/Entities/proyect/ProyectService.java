package com.admincorp.Entities.proyect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.admincorp.Login.User.Role;
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

        // Obtener el usuario autenticado y asignarlo como createdBy
        Users currentUser = getCurrentUser();
        entity.setCreatedBy(currentUser);

        return proyectoRepository.save(entity);
    }

    // Select
    public Proyect proyectoFindById(Long id) {
        Users currentUser = getCurrentUser();
        Proyect proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        if (canAccessProyect(proyecto, currentUser)) {
            return proyecto;
        } else {
            System.err.println("SecurityException: No tienes permiso para ver este proyecto");
            throw new SecurityException("No tienes permiso para ver este proyecto");
        }
    }

    // Select All
    public List<Proyect> proyectoFindAll() {
        Users currentUser = getCurrentUser();
        Iterable<Proyect> iterable = proyectoRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .filter(proyecto -> canAccessProyect(proyecto, currentUser))
                .collect(Collectors.toList());
    }

    // Update
    public Proyect proyectoUpdate(Long id, Proyect updatedProyecto) {
        Users currentUser = getCurrentUser();
        Proyect existingProyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        if (canModifyProyect(existingProyecto, currentUser)) {
            existingProyecto.setTitulo(updatedProyecto.getTitulo());
            existingProyecto.setDescripcion(updatedProyecto.getDescripcion());
            existingProyecto.setEstado(updatedProyecto.getEstado());

            return proyectoRepository.save(existingProyecto);
        } else {
            System.err.println("SecurityException: No tienes permiso para actualizar este proyecto");
            throw new SecurityException("No tienes permiso para actualizar este proyecto");
        }
    }

    // Delete
    public void proyectoDeleteById(Long id) {
        Proyect proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        Users currentUser = getCurrentUser();

        if (canModifyProyect(proyecto, currentUser)) {
            proyectoRepository.deleteById(id);
        } else {
            System.err.println("SecurityException: No tienes permiso para eliminar este proyecto");
            throw new SecurityException("No tienes permiso para eliminar este proyecto");
        }
    }

    // Método auxiliar para verificar si el usuario puede acceder al proyecto
    private boolean canAccessProyect(Proyect proyecto, Users currentUser) {
        return currentUser.getRole() == Role.ADMIN || proyecto.getCreatedBy().getId().equals(currentUser.getId());
    }

    // Método auxiliar para verificar si el usuario puede modificar el proyecto
    private boolean canModifyProyect(Proyect proyecto, Users currentUser) {
        return currentUser.getRole() == Role.ADMIN || proyecto.getCreatedBy().getId().equals(currentUser.getId());
    }

    // Obtener el usuario actualmente autenticado
    private Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("No authenticated user found");
        }
        return (Users) auth.getPrincipal();
    }
}