package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Conta;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.entity.Taxa;

import java.math.BigDecimal;

public record PagamentoRegistroDTO(

        Conta conta,
        BigDecimal boleto,
        BigDecimal valorPago,
        String dataPagamento,
        String status,
        Taxa taxa
) {

    public Pagamento toEntity(){
        return Pagamento.builder()
                .conta(conta)
                .boleto(boleto)
                .valorPago(valorPago)
                .dataPagamento(dataPagamento)
                .status(status)
                .taxa(taxa)
                .build();
            }
}
