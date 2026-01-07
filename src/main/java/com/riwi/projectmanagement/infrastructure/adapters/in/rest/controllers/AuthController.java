package com.riwi.projectmanagement.infrastructure.adapters.in.rest.controllers;

import com.riwi.projectmanagement.domain.ports.in.LoginUserCommand;
import com.riwi.projectmanagement.domain.ports.in.LoginUserUseCase;
import com.riwi.projectmanagement.domain.ports.in.RegisterUserCommand;
import com.riwi.projectmanagement.domain.ports.in.RegisterUserUseCase;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.response.AuthResponse;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.request.LoginRequest;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.request.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST - Autenticación y registro de usuarios
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API para autenticación y registro de usuarios")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    /**
     * Endpoint: POST /api/auth/register
     * Registra un nuevo usuario
     */
    @PostMapping("/register")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea una nueva cuenta de usuario y devuelve un token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario/email ya existe")
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // 1. Convertir Request a Command
        RegisterUserCommand command = new RegisterUserCommand(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        // 2. Ejecutar el caso de uso
        String token = registerUserUseCase.execute(command);

        // 3. Crear la respuesta
        AuthResponse response = new AuthResponse(token);

        // 4. Devolver respuesta HTTP 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Endpoint: POST /api/auth/login
     * Autentica un usuario existente
     */
    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario y devuelve un token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // 1. Convertir Request a Command
        LoginUserCommand command = new LoginUserCommand(
                request.getUsername(),
                request.getPassword()
        );

        // 2. Ejecutar el caso de uso
        String token = loginUserUseCase.execute(command);

        // 3. Crear la respuesta
        AuthResponse response = new AuthResponse(token);

        // 4. Devolver respuesta HTTP 200 OK
        return ResponseEntity.ok(response);
    }
}
