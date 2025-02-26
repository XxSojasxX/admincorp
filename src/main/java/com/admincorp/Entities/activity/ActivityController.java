package com.admincorp.Entities.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admincorp.Login.User.Role;
import com.admincorp.Login.User.Users;

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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LEADER')")
    @Operation(summary = "Crea una Actividad")
    public Activity save(@RequestBody Activity entity) {
        return activityService.activitySave(entity);
    }

    // Select
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LEADER', 'STAFF')")
    @Operation(summary = "Busca una Actividad por id")
    public Activity findById(@PathVariable("id") Long id) {
        return activityService.activityFindById(id);
    }

    // Select All
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LEADER', 'STAFF')")
    @Operation(summary = "Busca todas las actividades")
    public List<Activity> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) authentication.getPrincipal();
        if (user.getRole() == Role.ADMIN) {
            return activityService.activityFindAll();
        } else {
            return activityService.findActivitiesByStaffId(user.getId());
        }
    }

    // Select Activities and Projects by Staff ID
    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LEADER', 'STAFF')")
    @Operation(summary = "Busca proyectos y actividades asignadas a un staff por id de staff")
    public ResponseEntity<List<Activity>> findActivitiesByStaffId(@PathVariable("staffId") Long staffId) {
        List<Activity> activities = activityService.findActivitiesByStaffId(staffId);
        if (activities != null && !activities.isEmpty()) {
            return ResponseEntity.ok(activities);
        } else {
            throw new EntityNotFoundException("No se encontraron actividades para el staff con id " + staffId);
        }
    }

    // Update
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LEADER', 'STAFF')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LEADER')")
    @Operation(summary = "Elimina una Actividad por id")
    public void delete(@PathVariable("id") Long id) {
        activityService.activityDeleteById(id);
    }
}
