package com.riwi.projectmanagement.infrastructure.adapters.out.security;

import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Adaptador TEMPORAL - Simula un usuario autenticado
 * Más adelante lo reemplazaremos con JWT real
 */
@Component
public class CurrentUserAdapter implements CurrentUserPort {

    // Usuario temporal de prueba
    private static final UUID TEMP_USER_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Override
    public UUID getCurrentUserId() {
        // TODO: Más adelante obtener el usuario real desde el JWT
        return TEMP_USER_ID;
    }
}