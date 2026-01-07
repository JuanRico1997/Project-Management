package com.riwi.projectmanagement.domain.ports.out;

import java.util.UUID;

/**
 * Puerto OUT - Define la necesidad de generar tokens JWT
 */
public interface JwtServicePort {

    /**
     * Genera un token JWT para un usuario
     */
    String generateToken(UUID userId, String username);

    /**
     * Extrae el userId de un token JWT
     */
    UUID extractUserId(String token);

    /**
     * Extrae el username de un token JWT
     */
    String extractUsername(String token);
}