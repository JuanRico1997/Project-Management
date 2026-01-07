package com.riwi.projectmanagement.infrastructure.security;

import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Adaptador TEMPORAL - Usuario simulado
 */
@Component
public class CurrentUserAdapter implements CurrentUserPort {

    // Usuario temporal de prueba
    private static final UUID TEMP_USER_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Override
    public UUID getCurrentUserId() {
        return TEMP_USER_ID;
    }
}