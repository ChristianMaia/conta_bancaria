package com.example.conta_bancaria_mqtt.application.dto;

import java.math.BigDecimal;

public record PagamentoTaxaDTO(
        BigDecimal valorPago,
        BigDecimal percentual,
        BigDecimal valorFixo
) {
}
