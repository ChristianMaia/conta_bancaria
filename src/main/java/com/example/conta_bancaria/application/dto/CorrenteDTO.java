package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.ContaCorrente;

public record CorrenteDTO(
    Long id,
    String numero,
    Double saldo,
    String limite,
    Double taxa
) {
    public static CorrenteDTO fromEntity(ContaCorrente corrente) {
        return new CorrenteDTO(
            corrente.getId(),
            corrente.getNumero(),
            corrente.getSaldo(),
            corrente.getLimite(),
            corrente.getTaxa()
        );
    }

    public ContaCorrente toEntity() {
        ContaCorrente corrente = new ContaCorrente();
        corrente.setId(this.id);
        corrente.setNumero(this.numero);
        corrente.setSaldo(this.saldo);
        corrente.setLimite(this.limite);
        corrente.setTaxa(this.taxa);
        return corrente;
    }
}
