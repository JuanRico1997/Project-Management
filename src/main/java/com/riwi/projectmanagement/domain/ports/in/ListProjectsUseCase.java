package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Project;

import java.util.List;

/**
 * Puerto IN - Define el caso de uso "Listar Proyectos"
 */
public interface ListProjectsUseCase {
    /**
     * Lista todos los proyectos del usuario autenticado (no eliminados)
     * @return Lista de proyectos
     */
    List<Project> execute();
}
