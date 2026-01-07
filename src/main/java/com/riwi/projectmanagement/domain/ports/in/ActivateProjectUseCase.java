package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Project;

/**
 * Puerto IN - Define el caso de uso "Activar Proyecto"
 */
public interface ActivateProjectUseCase {
    /**
     * Activa un proyecto
     * Reglas:
     * - El proyecto debe tener al menos una tarea activa
     * - Solo el propietario puede activarlo
     * - Genera auditoría y notificación
     *
     * @param command Datos del proyecto a activar
     * @return El proyecto activado
     */
    Project execute(ActivateProjectCommand command);
}