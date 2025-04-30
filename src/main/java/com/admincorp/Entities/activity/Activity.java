package com.admincorp.Entities.activity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.admincorp.Login.User.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

@Entity
@Data
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreActividad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignado_id", nullable = false)
    @JsonBackReference("user-activity-asignado")
    private Users asignado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    @JsonBackReference("user-activity-leader")
    private Users leader;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime tiempoEntrega;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime deleteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @JsonBackReference("user-activity-createdBy")
    private Users createdBy;

    // Métodos getter y setter para asignado
    public Users getAsignado() {
        return asignado;
    }

    public void setAsignado(Users asignado) {
        this.asignado = asignado;
    }

    // Métodos getter y setter para leader
    public Users getLeader() {
        return leader;
    }

    public void setLeader(Users leader) {
        this.leader = leader;
    }

    // Métodos getter y setter para createdBy
    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }
}
