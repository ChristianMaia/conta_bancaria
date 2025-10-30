package com.example.conta_bancaria_mqtt.application.dto;

public class AuthDTO {

    public record LoginRequest(
            String cpf,
            String senha
    ) {}
    public record TokenResponse(
            String token
    ) {}
}
