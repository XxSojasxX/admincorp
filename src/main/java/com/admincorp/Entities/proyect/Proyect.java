package com.admincorp.Entities.proyect;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.admincorp.Login.User.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

@Entity
@Data
public class Proyect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    @JsonBackReference("user-proyecto-lider")
    private Users leader;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime deleteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @JsonBackReference("user-proyecto-createdBy")
    private Users createdBy;

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