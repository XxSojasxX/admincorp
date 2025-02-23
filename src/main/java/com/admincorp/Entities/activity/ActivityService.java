package com.admincorp.Entities.activity;

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
        existingActivity.setLeader(updatedActivity.getLeader());

        return activityRepository.save(existingActivity);
    }

    // Delete
    public void activityDeleteById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        activityRepository.deleteById(id);
    }
}
