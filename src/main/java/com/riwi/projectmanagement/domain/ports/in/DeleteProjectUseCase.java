package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Project;

/**
 * Puerto IN - Define el caso de uso "Eliminar Proyecto"
 */
public interface DeleteProjectUseCase {
    /**
     * Elimina lógicamente un proyecto
     * Reglas:
     * - Solo el propietario puede eliminarlo
     * - No puede estar ya eliminado
     *
     * @param command Datos del proyecto a eliminar
     * @return El proyecto eliminado
     */
    Project execute(DeleteProjectCommand command);
}