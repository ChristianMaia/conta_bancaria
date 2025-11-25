package com.example.conta_bancaria_mqtt.domain.service;

import com.example.conta_bancaria_mqtt.application.dto.PagamentoTaxaDTO;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.entity.Taxa;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public class PagamentoDomainService {

    protected BigDecimal TaxaPercentual(Pagamento valor, Taxa percentual){
        BigDecimal cem = new BigDecimal("100");
        BigDecimal valorTaxa = valor.getValorPago().multiply(percentual.getPercentual()).divide(cem);
        return valorTaxa;
    }

    protected BigDecimal TaxaValorFixo(Pagamento pagamento, Taxa valorFixo){
        BigDecimal valorTaxa = pagamento.getValorPago().subtract(valorFixo.getValorFixo());
        return valorTaxa;
    }

}
