package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Conta;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.entity.Taxa;
import com.example.conta_bancaria_mqtt.domain.enums.PagamentoStatus;

import java.math.BigDecimal;
import java.util.List;

public record PagamentoRegistroDTO(

        Conta conta,
        String boleto,
        BigDecimal valorPago,
        String dataPagamento,
        PagamentoStatus status,
        List<Taxa> taxas
) {

    public Pagamento toEntity(){
        return Pagamento.builder()
                .conta(conta)
                .boleto(boleto)
                .valorPago(valorPago)
                .dataPagamento(dataPagamento)
                .status(status)
                .taxas(taxas)
                .build();
            }
}
