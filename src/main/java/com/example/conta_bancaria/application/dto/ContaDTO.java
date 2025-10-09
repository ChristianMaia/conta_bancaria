package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ContaDTO(
        @Pattern(regexp = "\\d{5,20}", message = "Número da conta deve conter entre 5 a 20 digitos")
        String numero,

        String tipo,
        BigDecimal saldo
) {
}
