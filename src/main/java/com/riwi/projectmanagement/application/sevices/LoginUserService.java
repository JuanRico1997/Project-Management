package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.User;
import com.riwi.projectmanagement.domain.ports.in.LoginUserCommand;
import com.riwi.projectmanagement.domain.ports.in.LoginUserUseCase;
import com.riwi.projectmanagement.domain.ports.out.JwtServicePort;
import com.riwi.projectmanagement.domain.ports.out.PasswordEncoderPort;
import com.riwi.projectmanagement.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * Service - Implementa el caso de uso "Login Usuario"
 */
@Service
public class LoginUserService implements LoginUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtServicePort jwtService;

    public LoginUserService(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            JwtServicePort jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String execute(LoginUserCommand command) {
        // 1. Buscar el usuario por username
        User user = userRepository.findByUsername(command.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Credenciales inválidas"
                ));

        // 2. Validar la contraseña
        boolean passwordMatches = passwordEncoder.matches(
                command.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // 3. Generar y devolver el token JWT
        String token = jwtService.generateToken(user.getId(), user.getUsername());

        return token;
    }
}