package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.ports.in.ListProjectsUseCase;
import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanagement.domain.ports.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service - Implementa el caso de uso "Listar Proyectos"
 */
@Service
public class ListProjectsService implements ListProjectsUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public ListProjectsService(
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public List<Project> execute() {
        // 1. Obtener el usuario autenticado
        UUID currentUserId = currentUserPort.getCurrentUserId();

        // 2. Buscar todos sus proyectos (no eliminados)
        List<Project> projects = projectRepository.findByOwnerId(currentUserId);

        // 3. Devolver la lista
        return projects;
    }
}
