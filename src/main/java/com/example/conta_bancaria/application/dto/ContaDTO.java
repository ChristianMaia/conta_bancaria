package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;

public record ContaDTO(
        Long id,
        String numero,
        Double saldo,
        String tipo, // "corrente" ou "poupanca"
        String limite,  // só para corrente
        Double taxa,    // só para corrente
        Double rendimento // só para poupanca
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
