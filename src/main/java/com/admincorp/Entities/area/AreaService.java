package com.admincorp.Entities.area;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    // Insert
    public Area areaSave(Area entity) {
        return areaRepository.save(entity);
    }

    // Select
    public Area areaFindById(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        if (area.isDeleted()) {
            throw new EntityNotFoundException("Área no encontrada");
        }

        return area;
    }

    // Select All
    public List<Area> areaFindAll() {
        Iterable<Area> iterable = areaRepository.findAllByDeletedFalse();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Update
    public Area areaUpdate(Long id, Area updatedArea) {
        Area existingArea = areaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        if (updatedArea.getNombre() != null) {
            existingArea.setNombre(updatedArea.getNombre());
        }
        if (updatedArea.getDescripcion() != null) {
            existingArea.setDescripcion(updatedArea.getDescripcion());
        }

        return areaRepository.save(existingArea);
    }

    // Delete (Logical)
    public void areaDeleteById(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        area.setDeleted(true);
        area.setDeleteAt(LocalDateTime.now());
        areaRepository.save(area);
    }
}
