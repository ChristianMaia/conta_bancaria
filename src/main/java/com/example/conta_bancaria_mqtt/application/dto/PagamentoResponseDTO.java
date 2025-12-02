package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Conta;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;

import java.math.BigDecimal;
import java.util.List;

public record PagamentoResponseDTO(
        Long id,
        Conta conta,
        BigDecimal boleto,
        BigDecimal valorPago,
        String dataPagamento,
        String status,
        List<TaxaResponseDTO> taxa
) {
    public static PagamentoResponseDTO fromEntity(Pagamento pagamento){
        List<TaxaResponseDTO> taxas = pagamento.getTaxas().stream().map(TaxaResponseDTO::fromEntity).toList();
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getConta(),
                pagamento.getBoleto(),
                pagamento.getValorPago(),
                pagamento.getDataPagamento(),
                pagamento.getStatus(),
                taxas
        );
    }
}
