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
    public Activity activitySave(@RequestBody Activity entity) {
        return activityService.activitySave(entity);
    }

    // Select
    @GetMapping("/{id}/")
    @Operation(summary = "Busca una Actividad por id")
    public Activity activityFindById(@PathVariable Long id) {
        return activityService.activityFindById(id);
    }

    // Select All
    @GetMapping
    @Operation(summary = "Busca todas las actividades")
    public List<Activity> activityFindAll() {
        return activityService.activityFindAll();
    }

    // Update
    @PutMapping("/update/{id}/")
    @Operation(summary = "Actualiza una Actividad")
    public ResponseEntity<Activity> activityUpdate(@PathVariable Long id, @RequestBody Activity updatedActivity) {
        Activity existingActivity = activityService.activityFindById(id);
        if (existingActivity != null) {
            existingActivity.setNombreActividad(updatedActivity.getNombreActividad());
            existingActivity.setDescripcion(updatedActivity.getDescripcion());
            existingActivity.setEstado(updatedActivity.getEstado());
            existingActivity.setTiempoEntrega(updatedActivity.getTiempoEntrega());
            existingActivity.setProyecto(updatedActivity.getProyecto());
            existingActivity.setStaff(updatedActivity.getStaff());
            // Actualizar otros campos según sea necesario

            return ResponseEntity.ok(activityService.activitySave(existingActivity));
        } else {
            throw new EntityNotFoundException("Actividad con id " + id + " no encontrada");
        }
    }

    // Delete (Logical)
    @DeleteMapping("/{id}/")
    @Operation(summary = "Elimina una Actividad por id")
    public void activityDelete(@PathVariable Long id) {
        activityService.activityDeleteById(id);
    }
}
