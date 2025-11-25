package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Taxa;

import java.math.BigDecimal;

public record TaxaResponseDTO(Long id,
                              String descricao,
                              BigDecimal percentual,
                              BigDecimal valorFixo
) {
    public static TaxaResponseDTO fromEntity(Taxa taxa) {
        return new TaxaResponseDTO(
                taxa.getId(),
                taxa.getDescricao(),
                taxa.getPercentual(),
                taxa.getValorFixo()
        );
    }
}