package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Taxa;

import java.math.BigDecimal;

public record TaxaRegistroDTO(
        String descricao,
        BigDecimal percentual,
        BigDecimal valorfixo
) {
    public Taxa toEntity(){
        return Taxa.builder()
                .descricao(descricao)
                .percentual(percentual)
                .valorFixo(valorfixo)
                .build();
    }
}
