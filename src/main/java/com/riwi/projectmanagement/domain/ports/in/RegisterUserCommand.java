package com.riwi.projectmanagement.domain.ports.in;

/**
 * Command - Datos para registrar un usuario
 */
public class RegisterUserCommand {
    private final String username;
    private final String email;
    private final String password;

    public RegisterUserCommand(String username, String email, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        this.username = username.trim();
        this.email = email.trim().toLowerCase();
        this.password = password;
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