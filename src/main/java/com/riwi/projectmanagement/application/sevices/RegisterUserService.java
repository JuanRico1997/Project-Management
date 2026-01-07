package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.User;
import com.riwi.projectmanagement.domain.ports.in.RegisterUserCommand;
import com.riwi.projectmanagement.domain.ports.in.RegisterUserUseCase;
import com.riwi.projectmanagement.domain.ports.out.JwtServicePort;
import com.riwi.projectmanagement.domain.ports.out.PasswordEncoderPort;
import com.riwi.projectmanagement.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * Service - Implementa el caso de uso "Registrar Usuario"
 */
@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtServicePort jwtService;

    public RegisterUserService(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            JwtServicePort jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String execute(RegisterUserCommand command) {
        // 1. Validar que el username no exista
        boolean usernameExists = userRepository.existsByUsername(command.getUsername());
        if (usernameExists) {
            throw new IllegalArgumentException("El username ya está en uso");
        }

        // 2. Validar que el email no exista
        boolean emailExists = userRepository.existsByEmail(command.getEmail());
        if (emailExists) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        // 3. Encriptar la contraseña
        String encodedPassword = passwordEncoder.encode(command.getPassword());

        // 4. Crear el usuario (modelo de dominio)
        User newUser = new User(
                command.getUsername(),
                command.getEmail(),
                encodedPassword
        );

        // 5. Guardar el usuario
        User savedUser = userRepository.save(newUser);

        // 6. Generar y devolver el token JWT
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getUsername());

        return token;
    }
}
