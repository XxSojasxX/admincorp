package com.admincorp.Entities.employee;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.admincorp.Entities.area.Area;

import java.time.LocalDateTime;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cedula;

    @Column(nullable = true) // Hacer opcional
    private String nombreEmpleado;

    @Column(nullable = true) // Hacer opcional
    private String apellidoEmpleado;

    @Column(nullable = true) // Hacer opcional
    private String correoEmpleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = true)
    private Area area;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime deleteAt;

    @Column(nullable = false)
    private boolean deleted = false;

    // Métodos getter y setter para deleteAt
    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    // Métodos getter y setter para area
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}