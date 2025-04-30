package com.admincorp.Entities.activity;

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
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    // Insert
    public Activity activitySave(Activity entity) {
        // Verificar que el asignado y el líder no sean nulos
        if (entity.getAsignado() == null || entity.getAsignado().getId() == null) {
            throw new IllegalArgumentException("Asignado no puede ser nulo");
        }
        if (entity.getLeader() == null || entity.getLeader().getId() == null) {
            throw new IllegalArgumentException("Líder no puede ser nulo");
        }

        // Obtener el usuario autenticado y asignarlo como createdBy
        Users currentUser = getCurrentUser();
        entity.setCreatedBy(currentUser);

        return activityRepository.save(entity);
    }

    // Select
    public Activity activityFindById(Long id) {
        Users currentUser = getCurrentUser();
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        if (canAccessActivity(activity, currentUser)) {
            return activity;
        } else {
            System.err.println("SecurityException: No tienes permiso para ver esta actividad");
            throw new SecurityException("No tienes permiso para ver esta actividad");
        }
    }

    // Select All
    public List<Activity> activityFindAll() {
        Users currentUser = getCurrentUser();
        Iterable<Activity> iterable = activityRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .filter(activity -> canAccessActivity(activity, currentUser))
                .collect(Collectors.toList());
    }

    // Update
    public Activity activityUpdate(Long id, Activity updatedActivity) {
        Users currentUser = getCurrentUser();
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        if (canModifyActivity(existingActivity, currentUser)) {
            existingActivity.setNombreActividad(updatedActivity.getNombreActividad());
            existingActivity.setDescripcion(updatedActivity.getDescripcion());
            existingActivity.setEstado(updatedActivity.getEstado());
            existingActivity.setTiempoEntrega(updatedActivity.getTiempoEntrega());
            existingActivity.setLeader(updatedActivity.getLeader());

            return activityRepository.save(existingActivity);
        } else {
            System.err.println("SecurityException: No tienes permiso para actualizar esta actividad");
            throw new SecurityException("No tienes permiso para actualizar esta actividad");
        }
    }

    // Delete
    public void activityDeleteById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        Users currentUser = getCurrentUser();

        if (canModifyActivity(activity, currentUser)) {
            activityRepository.deleteById(id);
        } else {
            System.err.println("SecurityException: No tienes permiso para eliminar esta actividad");
            throw new SecurityException("No tienes permiso para eliminar esta actividad");
        }
    }

    // Método auxiliar para verificar si el usuario puede acceder a la actividad
    private boolean canAccessActivity(Activity activity, Users currentUser) {
        return currentUser.getRole() == Role.ADMIN || activity.getCreatedBy().getId().equals(currentUser.getId());
    }

    // Método auxiliar para verificar si el usuario puede modificar la actividad
    private boolean canModifyActivity(Activity activity, Users currentUser) {
        return currentUser.getRole() == Role.ADMIN || activity.getCreatedBy().getId().equals(currentUser.getId());
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
