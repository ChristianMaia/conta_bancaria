package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.CodigoAutenticacao;

import java.time.LocalDateTime;

public record CodigoResponseDTO(
        Long id,
        String codigo,
        LocalDateTime expiraEm,
        boolean validado,
        String clienteCpf
) {
    public static CodigoResponseDTO fromEntity(CodigoAutenticacao entity){
        return new CodigoResponseDTO(
                entity.getId(),
                entity.getCodigo(),
                entity.getExpiraEm(),
                entity.isValidado(),
                entity.getCliente().getCpf()
        );
    }

}
