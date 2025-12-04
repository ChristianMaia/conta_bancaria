package com.example.conta_bancaria_mqtt.domain.service;

import com.example.conta_bancaria_mqtt.application.dto.PagamentoTaxaDTO;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.entity.Taxa;
import com.example.conta_bancaria_mqtt.domain.enums.PagamentoStatus;
import com.example.conta_bancaria_mqtt.domain.exception.PagamentoInvalidoException;
import com.example.conta_bancaria_mqtt.domain.exception.SaldoInsuficienteException;
import com.example.conta_bancaria_mqtt.domain.repository.PagamentoRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDomainService {
    private static PagamentoRepository repository;

    public static void validarPagamento(Pagamento pagamento) {
        if (pagamento.getValorPago() == null || pagamento.getValorPago().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PagamentoInvalidoException("Valor do pagamento deve ser positivo.");
        }

        if (pagamento.getConta() == null || !pagamento.getConta().isAtiva()) {
            throw new PagamentoInvalidoException("Conta inválida ou inativa.");
        }

        if (pagamento.getConta().getSaldo().compareTo(pagamento.getValorPago()) < 0) {
            throw new SaldoInsuficienteException("pagamento");
        }

        if (pagamento.getTipoPagamento() == null) {
            throw new PagamentoInvalidoException("Tipo de pagamento inválido/inexistente. Verfique se digitou corretamente: LUZ, AGUA ou BOLETO.");
        }

    }

    public static void calcularTaxa(Pagamento pagamento, List<Taxa> taxasAtivas) {

        // define as taxas do pagamento com base no que veio do banco
        if (taxasAtivas != null && !taxasAtivas.isEmpty()) {
            // cria uma cópia das taxas para este pagamento específico
            pagamento.setTaxas(new ArrayList<>(taxasAtivas));
        }

        BigDecimal valorBase = pagamento.getValorPago();
        BigDecimal totalTaxas = BigDecimal.ZERO;

        // faz o calculo do percentual, se tiver, e soma com o valorFixo, se tiver
        if (pagamento.getTaxas() != null) {
            for (Taxa taxa : pagamento.getTaxas()) {
                if (taxa.getPercentual() != null && taxa.getPercentual().compareTo(BigDecimal.ZERO) > 0) {
                    totalTaxas = totalTaxas.add(valorBase.multiply(taxa.getPercentual()));
                }
                if (taxa.getValorFixo() != null && taxa.getValorFixo().compareTo(BigDecimal.ZERO) > 0) {
                    totalTaxas = totalTaxas.add(taxa.getValorFixo());
                }
            }
        }
        pagamento.setValorPago(valorBase.add(totalTaxas));
    }

    public static void definirStatus(Pagamento pagamento, boolean respostaValidaMQTT) {
        if (!respostaValidaMQTT) {
            pagamento.setStatus(PagamentoStatus.FALHA);
            return;
        }

        if (pagamento.getConta().getSaldo().compareTo(pagamento.getValorPago()) >= 0) {
            pagamento.setStatus(PagamentoStatus.SUCESSO);
            // Debita o valor da conta
            pagamento.getConta().sacar(pagamento.getValorPago());
        } else {
            pagamento.setStatus(PagamentoStatus.FALHA);
        }
    }
}
