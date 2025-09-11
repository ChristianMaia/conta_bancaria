package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.ContaPoupanca;

public record PoupancaDTO(
    Long id,
    String nome,
    Double saldo,
    Double rendimento
) {
    public static PoupancaDTO fromEntity(ContaPoupanca poupanca) {
        return new PoupancaDTO(
            poupanca.getId(),
            poupanca.getNumero(),
            poupanca.getSaldo(),
            poupanca.getRendimento()
        );
    }

    public com.example.conta_bancaria.domain.entity.ContaPoupanca toEntity() {
        com.example.conta_bancaria.domain.entity.ContaPoupanca poupanca = new com.example.conta_bancaria.domain.entity.ContaPoupanca();
        poupanca.setId(this.id);
        poupanca.setNumero(this.nome);
        poupanca.setSaldo(this.saldo);
        poupanca.setRendimento(this.rendimento);
        return poupanca;
    }
}
