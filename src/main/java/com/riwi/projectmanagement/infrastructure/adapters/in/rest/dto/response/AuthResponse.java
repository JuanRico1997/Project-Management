package com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.response;

/**
 * DTO - Response de autenticación (contiene el token JWT)
 */
public class AuthResponse {
    private String token;
    private String type = "Bearer";

    // Constructor vacío
    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
