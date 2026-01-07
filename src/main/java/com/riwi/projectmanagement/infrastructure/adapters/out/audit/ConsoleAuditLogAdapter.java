package com.riwi.projectmanagement.infrastructure.adapters.out.audit;

import com.riwi.projectmanagement.domain.ports.out.AuditLogPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Adaptador - Implementa auditoría mostrando en consola
 * En producción, esto guardaría en una BD o servicio externo
 */
@Component
public class ConsoleAuditLogAdapter implements AuditLogPort {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void register(String action, UUID entityId) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format(
                "[AUDIT] %s | Action: %s | EntityId: %s",
                timestamp,
                action,
                entityId
        );

        System.out.println(logEntry);

        // TODO: En producción, guardar en base de datos:
        // auditRepository.save(new AuditLog(action, entityId, timestamp));
    }
}