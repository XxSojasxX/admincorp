package com.admincorp.Entities.activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return activity;
    }

    // Select All
    public List<Activity> activityFindAll() {
        Iterable<Activity> iterable = activityRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Update
    public Activity activityUpdate(Long id, Activity updatedActivity) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        existingActivity.setNombreActividad(updatedActivity.getNombreActividad());
        existingActivity.setDescripcion(updatedActivity.getDescripcion());
        existingActivity.setEstado(updatedActivity.getEstado());
        existingActivity.setTiempoEntrega(updatedActivity.getTiempoEntrega());
        existingActivity.setProyecto(updatedActivity.getProyecto());
        existingActivity.setStaff(updatedActivity.getStaff());

        return activityRepository.save(existingActivity);
    }

    // Delete (Logical)
    public void activityDeleteById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        activity.setDeleteAt(LocalDateTime.now());
        activityRepository.save(activity);
    }
}
