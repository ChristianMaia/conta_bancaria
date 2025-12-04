package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Conta;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.enums.PagamentoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PagamentoResponseDTO(
        Long id,
        Conta conta,
        String boleto,
        BigDecimal valorPago,
        LocalDateTime dataPagamento,
        PagamentoStatus status,
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
