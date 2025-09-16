package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;

import java.math.BigDecimal;

public record ContaDTO(
        Long id,
        String numero,
        BigDecimal saldo,
        String tipo, // "corrente" ou "poupanca"
        BigDecimal limite,  // só para corrente
        BigDecimal taxa,    // só para corrente
        BigDecimal rendimento // só para poupanca
) {

    public static ContaDTO fromEntity(Conta conta) {
        if (conta == null) return null;

        if (conta instanceof ContaCorrente cc) {
            return new ContaDTO(
                    cc.getId(),
                    cc.getNumero(),
                    cc.getSaldo(),
                    "corrente",
                    cc.getLimite(),
                    cc.getTaxa(),
                    null
            );
        } else if (conta instanceof ContaPoupanca cp) {
            return new ContaDTO(
                    cp.getId(),
                    cp.getNumero(),
                    cp.getSaldo(),
                    "poupanca",
                    null,
                    null,
                    cp.getRendimento()
            );
        } else {
            // Caso base para outras implementações de Conta (se existirem)
            return new ContaDTO(
                    conta.getId(),
                    conta.getNumero(),
                    conta.getSaldo(),
                    "desconhecido",
                    null,
                    null,
                    null
            );
        }
    }
}
