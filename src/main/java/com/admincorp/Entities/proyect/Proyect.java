package com.admincorp.Entities.proyect;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import com.admincorp.Login.User.Users;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Proyect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    @JsonManagedReference
    private Users leader;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime deleteAt;

    @Column(nullable = false)
    private boolean deleted = false;

    // Métodos getter y setter para leader
    public Users getLeader() {
        return leader;
    }

    public void setLeader(Users leader) {
        this.leader = leader;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }
}