package com.admincorp.Entities.area;

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
@RequestMapping("/admincorp/areas")
@Tag(name = "Controlador de Áreas")
public class AreaController {
    
    @Autowired
    private AreaService areaService;

    // Create
    @PostMapping
    @Operation(summary = "Crea un Área")
    public Area areaSave(@RequestBody Area entity) {
        return areaService.areaSave(entity);
    }

    // Select
    @GetMapping("/{id}")
    @Operation(summary = "Busca un Área por id")
    public Area areaFindById(@PathVariable("id") Long id) {
        return areaService.areaFindById(id);
    }

    // Select All
    @GetMapping
    @Operation(summary = "Busca todas las áreas")
    public List<Area> areaFindAll() {
        return areaService.areaFindAll();
    }

    // Update
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un Área")
    public ResponseEntity<Area> areaUpdate(@PathVariable("id") Long id, @RequestBody Area updatedArea) {
        Area existingArea = areaService.areaFindById(id);
        if (existingArea != null) {
            if (updatedArea.getNombre() != null) {
                existingArea.setNombre(updatedArea.getNombre());
            }
            if (updatedArea.getDescripcion() != null) {
                existingArea.setDescripcion(updatedArea.getDescripcion());
            }

            return ResponseEntity.ok(areaService.areaSave(existingArea));
        } else {
            throw new EntityNotFoundException("Área con id " + id + " no encontrada");
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Área por id")
    public void areaDelete(@PathVariable("id") Long id) {
        areaService.areaDeleteById(id);
    }
}
