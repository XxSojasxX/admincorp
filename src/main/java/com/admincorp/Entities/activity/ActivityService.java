package com.admincorp.Entities.activity;

import java.time.LocalDateTime;
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
        // Verificar que el proyecto y el staff no sean nulos
        if (entity.getProyecto() == null || entity.getProyecto().getId() == null) {
            throw new IllegalArgumentException("Proyecto no puede ser nulo");
        }
        if (entity.getStaff() == null || entity.getStaff().getId() == null) {
            throw new IllegalArgumentException("Staff no puede ser nulo");
        }

        return activityRepository.save(entity);
    }

    // Select
    public Activity activityFindById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        if (activity.isDeleted()) {
            throw new EntityNotFoundException("Actividad no encontrada");
        }

        return activity;
    }

    // Select All
    public List<Activity> activityFindAll() {
        Iterable<Activity> iterable = activityRepository.findAllByDeletedFalse();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Select Activities and Projects by Staff ID
    public List<Activity> findActivitiesByStaffId(Long staffId) {
        return activityRepository.findByStaffId(staffId);
    }

    // Update
    public Activity activityUpdate(Long id, Activity updatedActivity) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        if (updatedActivity.getNombreActividad() != null) {
            existingActivity.setNombreActividad(updatedActivity.getNombreActividad());
        }
        if (updatedActivity.getDescripcion() != null) {
            existingActivity.setDescripcion(updatedActivity.getDescripcion());
        }
        if (updatedActivity.getEstado() != null) {
            existingActivity.setEstado(updatedActivity.getEstado());
        }
        if (updatedActivity.getTiempoEntrega() != null) {
            existingActivity.setTiempoEntrega(updatedActivity.getTiempoEntrega());
        }
        if (updatedActivity.getProyecto() != null) {
            existingActivity.setProyecto(updatedActivity.getProyecto());
        }
        if (updatedActivity.getStaff() != null) {
            existingActivity.setStaff(updatedActivity.getStaff());
        }

        return activityRepository.save(existingActivity);
    }

    // Delete (Logical)
    public void activityDeleteById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        activity.setDeleted(true);
        activity.setDeleteAt(LocalDateTime.now());
        activityRepository.save(activity);
    }
}
