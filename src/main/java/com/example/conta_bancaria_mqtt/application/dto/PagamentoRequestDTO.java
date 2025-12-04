package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Conta;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.enums.PagamentoStatus;
import com.example.conta_bancaria_mqtt.domain.enums.TipoPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public record PagamentoRequestDTO(
        String contaNumero,
        String boleto,
        BigDecimal valorPago,
        PagamentoStatus status,

        // escolha do tipo de pagamento do cliente
        TipoPagamento tipoPagamento,
        String codigoAutenticacao
) {
    public Pagamento toEntity(Conta conta) {

        return Pagamento.builder()
                .conta(conta)
                .boleto(this.boleto)
                .valorPago(this.valorPago)
                .dataPagamento(LocalDateTime.now())
                .status(PagamentoStatus.PENDENTE)
                .tipoPagamento(this.tipoPagamento)
                .taxas(new ArrayList<>())
                .build();
    }
}
