package com.admincorp.Entities.employee;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cedula;

    @Column(nullable = false)
    private String nombreEmpleado;

    @Column(nullable = false)
    private String apellidoEmpleado;

    @Column(nullable = false)
    private String correoEmpleado;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime deleteAt;

    // Relación con Users eliminada
}