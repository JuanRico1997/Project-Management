package com.riwi.projectmanagement.domain.models;

import java.util.UUID;

/**
 * Modelo de dominio PURO - Representa un Usuario
 * NO depende de JPA, Spring ni ningún framework
 */
public class User {
    private UUID id;
    private String username;
    private String email;
    private String password; // Hasheado

    // Constructor para crear usuario nuevo (registro)
    public User(String username, String email, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password; // Se hasheará antes de guardar
    }

    // Constructor completo (para recrear desde BD)
    public User(UUID id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // ========================================
    // MÉTODOS DE NEGOCIO
    // ========================================

    /**
     * Valida que el username sea único
     */
    public void validateUsername(boolean exists) {
        if (exists) {
            throw new IllegalArgumentException("El username ya está en uso");
        }
    }

    /**
     * Valida que el email sea único
     */
    public void validateEmail(boolean exists) {
        if (exists) {
            throw new IllegalArgumentException("El email ya está en uso");
        }
    }

    // ========================================
    // GETTERS
    // ========================================

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}