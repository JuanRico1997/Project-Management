package com.riwi.projectmanagement.infrastructure.adapters.in.rest.controllers;

import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.in.CreateTaskCommand;
import com.riwi.projectmanagement.domain.ports.in.CreateTaskUseCase;
import com.riwi.projectmanagement.domain.ports.in.ListTasksByProjectQuery;
import com.riwi.projectmanagement.domain.ports.in.ListTasksByProjectUseCase;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.request.CreateTaskRequest;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.response.TaskResponse;
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
 * Controller REST - Adaptador de entrada para Tareas
 */
@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@Tag(name = "Tasks", description = "API para gestión de tareas")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListTasksByProjectUseCase listTasksByProjectUseCase;


    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            ListTasksByProjectUseCase listTasksByProjectUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.listTasksByProjectUseCase = listTasksByProjectUseCase;
    }

    /**
     * Endpoint: GET /api/projects/{projectId}/tasks
     * Lista todas las tareas de un proyecto
     */
    @GetMapping  // ← NUEVO MÉTODO
    @Operation(
            summary = "Listar tareas de un proyecto",
            description = "Obtiene todas las tareas de un proyecto específico (no eliminadas)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No eres el propietario del proyecto"),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    public ResponseEntity<List<TaskResponse>> listTasks(@PathVariable UUID projectId) {
        // 1. Crear el query
        ListTasksByProjectQuery query = new ListTasksByProjectQuery(projectId);

        // 2. Ejecutar el caso de uso
        List<Task> tasks = listTasksByProjectUseCase.execute(query);

        // 3. Convertir de dominio a DTO Response
        List<TaskResponse> response = tasks.stream()
                .map(TaskResponse::fromDomain)
                .collect(Collectors.toList());

        // 4. Devolver respuesta HTTP 200 OK
        return ResponseEntity.ok(response);
    }


    /**
     * Endpoint: POST /api/projects/{projectId}/tasks
     * Crea una nueva tarea en un proyecto
     */
    @PostMapping
    @Operation(
            summary = "Crear una nueva tarea",
            description = "Crea una nueva tarea en un proyecto específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No eres el propietario del proyecto"),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable UUID projectId,
            @Valid @RequestBody CreateTaskRequest request) {

        // 1. Convertir Request a Command
        CreateTaskCommand command = new CreateTaskCommand(projectId, request.getTitle());

        // 2. Ejecutar el caso de uso
        Task createdTask = createTaskUseCase.execute(command);

        // 3. Convertir Task (dominio) a Response (HTTP)
        TaskResponse response = TaskResponse.fromDomain(createdTask);

        // 4. Devolver respuesta HTTP 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
