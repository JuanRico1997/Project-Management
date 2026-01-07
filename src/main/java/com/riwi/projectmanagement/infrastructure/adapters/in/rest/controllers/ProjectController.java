package com.riwi.projectmanagement.infrastructure.adapters.in.rest.controllers;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.ports.in.*;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.request.CreateProjectRequest;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.response.ProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller REST - Adaptador de entrada para Proyectos
 * Recibe peticiones HTTP y las convierte en casos de uso
 */
@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "API para gestión de proyectos")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ListProjectsUseCase listProjectsUseCase;
    private final ActivateProjectUseCase activateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    // Constructor actualizado
    public ProjectController(
            CreateProjectUseCase createProjectUseCase,
            ListProjectsUseCase listProjectsUseCase,
            ActivateProjectUseCase activateProjectUseCase,
            DeleteProjectUseCase deleteProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
        this.listProjectsUseCase = listProjectsUseCase;
        this.activateProjectUseCase = activateProjectUseCase;
        this.deleteProjectUseCase = deleteProjectUseCase;
    }

    /**
     * Endpoint: POST /api/projects
     * Crea un nuevo proyecto
     */
    @PostMapping
    @Operation(
            summary = "Crear un nuevo proyecto",
            description = "Crea un nuevo proyecto en estado DRAFT para el usuario autenticado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proyecto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest request) {

        // 1. Convertir Request (HTTP) a Command (dominio)
        CreateProjectCommand command = new CreateProjectCommand(request.getName());

        // 2. Ejecutar el caso de uso
        Project createdProject = createProjectUseCase.execute(command);

        // 3. Convertir Project (dominio) a Response (HTTP)
        ProjectResponse response = ProjectResponse.fromDomain(createdProject);

        // 4. Devolver respuesta HTTP 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Endpoint: GET /api/projects
     * Lista todos los proyectos del usuario autenticado
     */
    @GetMapping  // ← NUEVO MÉTODO
    @Operation(
            summary = "Listar proyectos",
            description = "Obtiene todos los proyectos del usuario autenticado (no eliminados)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proyectos obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<List<ProjectResponse>> listProjects() {
        // 1. Ejecutar el caso de uso
        List<Project> projects = listProjectsUseCase.execute();

        // 2. Convertir de dominio a DTO Response
        List<ProjectResponse> response = projects.stream()
                .map(ProjectResponse::fromDomain)
                .collect(Collectors.toList());

        // 3. Devolver respuesta HTTP 200 OK
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint: PATCH /api/projects/{id}/activate
     * Activa un proyecto
     */
    @PatchMapping("/{id}/activate")
    @Operation(
            summary = "Activar proyecto",
            description = "Activa un proyecto. Requiere que el proyecto tenga al menos una tarea activa."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyecto activado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El proyecto no cumple los requisitos para activarse"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No eres el propietario del proyecto"),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    public ResponseEntity<ProjectResponse> activateProject(@PathVariable UUID id) {
        // 1. Crear el command
        ActivateProjectCommand command = new ActivateProjectCommand(id);

        // 2. Ejecutar el caso de uso
        Project activatedProject = activateProjectUseCase.execute(command);

        // 3. Convertir a Response
        ProjectResponse response = ProjectResponse.fromDomain(activatedProject);

        // 4. Devolver respuesta HTTP 200 OK
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint: DELETE /api/projects/{id}
     * Elimina lógicamente un proyecto
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar proyecto",
            description = "Elimina lógicamente un proyecto (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyecto eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El proyecto ya está eliminado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No eres el propietario del proyecto"),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable UUID id) {
        // 1. Crear el command
        DeleteProjectCommand command = new DeleteProjectCommand(id);

        // 2. Ejecutar el caso de uso
        Project deletedProject = deleteProjectUseCase.execute(command);

        // 3. Convertir a Response
        ProjectResponse response = ProjectResponse.fromDomain(deletedProject);

        // 4. Devolver respuesta HTTP 200 OK
        return ResponseEntity.ok(response);
    }
}

