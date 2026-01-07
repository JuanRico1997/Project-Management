package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Project;

/**
 * Puerto IN - Define el caso de uso "Crear Proyecto"
 */
public interface CreateProjectUseCase {
    /**
     * Crea un nuevo proyecto
     * @param command Datos del proyecto a crear
     * @return El proyecto creado
     */
    Project execute(CreateProjectCommand command);
}
