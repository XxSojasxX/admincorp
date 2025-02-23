package com.admincorp.Entities.proyect;

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
@RequestMapping("/admincorp/proyectos")
@Tag(name = "Controlador de Proyectos")
public class ProyectController {
    
    @Autowired
    private ProyectService proyectoService;

    // Create
    @PostMapping
    @Operation(summary = "Crea un Proyecto")
    public Proyect proyectoSave(@RequestBody Proyect entity) {
        return proyectoService.proyectoSave(entity);
    }

    // Select
    @GetMapping("/{id}/")
    @Operation(summary = "Busca un Proyecto por id")
    public Proyect proyectoFindById(@PathVariable Long id) {
        return proyectoService.proyectoFindById(id);
    }

    // Select All
    @GetMapping
    @Operation(summary = "Busca todos los proyectos")
    public List<Proyect> proyectoFindAll() {
        return proyectoService.proyectoFindAll();
    }

    // Update
    @PutMapping("/update/{id}/")
    @Operation(summary = "Actualiza un Proyecto")
    public ResponseEntity<Proyect> proyectoUpdate(@PathVariable Long id, @RequestBody Proyect updatedProyect) {
        Proyect existingProyect = proyectoService.proyectoFindById(id);
        if (existingProyect != null) {
            existingProyect.setTitulo(updatedProyect.getTitulo());
            existingProyect.setDescripcion(updatedProyect.getDescripcion());
            existingProyect.setEstado(updatedProyect.getEstado());
            // Actualizar otros campos según sea necesario

            return ResponseEntity.ok(proyectoService.proyectoSave(existingProyect));
        } else {
            throw new EntityNotFoundException("Proyecto con id " + id + " no encontrado");
        }
    }

    // Delete
    @DeleteMapping("/{id}/")
    @Operation(summary = "Elimina un Proyecto por id")
    public void proyectoDelete(@PathVariable Long id) {
        proyectoService.proyectoDeleteById(id);
    }
}