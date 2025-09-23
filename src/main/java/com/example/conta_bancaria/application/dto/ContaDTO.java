package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;

import java.math.BigDecimal;

public record ContaDTO(
        String numero,
        String tipo,
        BigDecimal saldo
) {
}
