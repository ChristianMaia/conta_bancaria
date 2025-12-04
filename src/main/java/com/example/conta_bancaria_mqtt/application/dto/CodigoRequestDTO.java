package com.example.conta_bancaria_mqtt.application.dto;

import java.time.LocalDateTime;

public record CodigoRequestDTO(
        String codigo,
        LocalDateTime expiraEm,
        String clienteCpf
) {
}
