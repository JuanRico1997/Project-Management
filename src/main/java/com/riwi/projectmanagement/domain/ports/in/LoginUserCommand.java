package com.riwi.projectmanagement.domain.ports.in;

/**
 * Command - Datos para login de usuario
 */
public class LoginUserCommand {
    private final String username;
    private final String password;

    public LoginUserCommand(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        this.username = username.trim();
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}