package com.admincorp.Entities.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/admincorp/activities")
@Tag(name = "Controlador de Actividades")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;

    // Create
    @PostMapping
    @Operation(summary = "Crea una Actividad")
    public Activity save(@RequestBody Activity entity) {
        return activityService.activitySave(entity);
    }

    // Select
    @GetMapping("/{id}")
    @Operation(summary = "Busca una Actividad por id")
    public Activity findById(@PathVariable("id") Long id) {
        return activityService.activityFindById(id);
    }

    // Select All
    @GetMapping
    @Operation(summary = "Busca todas las actividades")
    public List<Activity> findAll() {
        return activityService.activityFindAll();
    }

    // Update
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una Actividad")
    public ResponseEntity<Activity> update(@PathVariable("id") Long id, @RequestBody Activity updatedActivity) {
        Activity existingActivity = activityService.activityFindById(id);
        if (existingActivity != null) {
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
            // Actualizar otros campos según sea necesario

            return ResponseEntity.ok(activityService.activitySave(existingActivity));
        } else {
            throw new EntityNotFoundException("Actividad con id " + id + " no encontrada");
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una Actividad por id")
    public void delete(@PathVariable("id") Long id) {
        activityService.activityDeleteById(id);
    }
}
