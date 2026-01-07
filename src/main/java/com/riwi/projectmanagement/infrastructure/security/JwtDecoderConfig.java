package com.riwi.projectmanagement.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtDecoderConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public JwtDecoder jwtDecoder() {
        // Convertimos el string secreto a bytes
        byte[] keyBytes = secretKey.getBytes();

        // Creamos el SecretKey compatible con Nimbus
        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");

        // Construimos el JwtDecoder
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
}
