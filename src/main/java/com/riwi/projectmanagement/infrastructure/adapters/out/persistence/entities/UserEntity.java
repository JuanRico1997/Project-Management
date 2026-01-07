package com.riwi.projectmanagement.infrastructure.adapters.out.persistence.entities;

import com.riwi.projectmanagement.domain.models.User;
import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entidad JPA - Representa la tabla 'users' en MySQL
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    // Constructor vacío (JPA lo requiere)
    public UserEntity() {
    }

    // Constructor completo
    public UserEntity(UUID id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // ============================================
    // MÉTODOS DE CONVERSIÓN
    // ============================================

    /**
     * Convierte de Entidad JPA a Modelo de Dominio
     */
    public User toDomain() {
        return new User(
                this.id,
                this.username,
                this.email,
                this.password
        );
    }

    /**
     * Convierte de Modelo de Dominio a Entidad JPA
     */
    public static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    // ============================================
    // GETTERS Y SETTERS
    // ============================================

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}