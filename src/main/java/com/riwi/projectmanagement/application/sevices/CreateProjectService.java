package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.ports.in.CreateProjectCommand;
import com.riwi.projectmanagement.domain.ports.in.CreateProjectUseCase;
import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanagement.domain.ports.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service - Implementa el caso de uso "Crear Proyecto"
 * Orquesta la lógica de negocio
 */
@Service
public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    // Spring inyecta automáticamente las dependencias
    public CreateProjectService(
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public Project execute(CreateProjectCommand command) {
        // 1. Obtener el usuario autenticado actual
        UUID currentUserId = currentUserPort.getCurrentUserId();

        // 2. Crear el proyecto (modelo de dominio)
        Project newProject = new Project(currentUserId, command.getName());

        // 3. Guardar el proyecto
        Project savedProject = projectRepository.save(newProject);

        // 4. Devolver el proyecto creado
        return savedProject;
    }
}
