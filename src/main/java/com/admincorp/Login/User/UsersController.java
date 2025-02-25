package com.admincorp.Login.User;

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
@RequestMapping("/admincorp/users")
@Tag(name = "Controlador de Usuarios")
public class UsersController {
    
    @Autowired
    private UserService usersService;

    // Create
    @PostMapping
    @Operation(summary = "Crea un Usuario")
    public Users userSave(@RequestBody Users entity) {
        return usersService.userSave(entity);
    }

    // Select
    @GetMapping("/{id}")
    @Operation(summary = "Busca un Usuario por id")
    public Users userFindById(@PathVariable Long id) {
        return usersService.findById(id);
    }

    // Select All
    @GetMapping
    @Operation(summary = "Busca todos los usuarios")
    public List<Users> userFindAll() {
        return usersService.findAll();
    }

    // Update
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un Usuario")
    public ResponseEntity<Users> userUpdate(@PathVariable Long id, @RequestBody Users updatedUser) {
        Users existingUser = usersService.findById(id);
        if (existingUser != null) {
            if (updatedUser.getUserName() != null) {
                existingUser.setUserName(updatedUser.getUserName());
            }
            if (updatedUser.getFirstName() != null) {
                existingUser.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null) {
                existingUser.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getRole() != null) {
                existingUser.setRole(updatedUser.getRole());
            }
            // Actualizar otros campos según sea necesario

            return ResponseEntity.ok(usersService.userSave(existingUser));
        } else {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Usuario por id")
    public void userDelete(@PathVariable Long id) {
        usersService.deleteById(id);
    }
}

