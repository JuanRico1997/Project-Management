package com.riwi.projectmanagement.domain.ports.out;

/**
 * Puerto OUT - Define la necesidad de encriptar contraseñas
 */
public interface PasswordEncoderPort {

    /**
     * Encripta una contraseña
     */
    String encode(String rawPassword);

    /**
     * Verifica si una contraseña coincide con su hash
     */
    boolean matches(String rawPassword, String encodedPassword);
}