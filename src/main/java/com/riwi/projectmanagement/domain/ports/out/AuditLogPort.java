package com.riwi.projectmanagement.domain.ports.out;

import java.util.UUID;

/**
 * Puerto OUT - Define la necesidad de registrar auditoría
 */
public interface AuditLogPort {
    /**
     * Registra una acción en el log de auditoría
     * @param action Acción realizada (ej: "PROJECT_ACTIVATED")
     * @param entityId ID de la entidad afectada
     */
    void register(String action, UUID entityId);
}
