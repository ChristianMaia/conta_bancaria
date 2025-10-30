package com.example.conta_bancaria_mqtt.application.dto;

import java.math.BigDecimal;

public record ContaAtualizacaoDTO (
        BigDecimal saldo,
        BigDecimal limite,
        BigDecimal rendimento,
        BigDecimal taxa
){
}
