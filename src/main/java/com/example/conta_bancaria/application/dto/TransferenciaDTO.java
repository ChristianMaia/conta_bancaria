package com.example.conta_bancaria.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank
        @Pattern(regexp = "\\d{5,20}", message = "Número da conta deve conter entre 5 a 20 digitos")
        String contaDestino,
        BigDecimal valor
) {

}
