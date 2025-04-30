package com.admincorp.Entities.employee;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.admincorp.Login.User.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    @JsonBackReference("user-employee-usuario")
    private Users usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @JsonBackReference("user-employee-createdBy")
    private Users createdBy;

    // Métodos getter y setter para usuario
    public Users getUsuario() {
        return usuario;
    }

    public void setUsuario(Users usuario) {
        this.usuario = usuario;
    }

    // Métodos getter y setter para createdBy
    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }
}