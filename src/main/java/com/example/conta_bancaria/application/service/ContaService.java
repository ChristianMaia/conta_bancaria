package com.example.conta_bancaria.application.service;

import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.repository.CorrenteRepository;
import com.example.conta_bancaria.domain.repository.PoupancaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    @Autowired
    private CorrenteRepository correnteRepository;
    @Autowired
    private PoupancaRepository poupancaRepository;



    public Conta atualizarSaldo(Conta conta, Double novoSaldo) {
        // Atualiza o saldo com base no tipo da conta
        if (conta instanceof ContaCorrente) {
            return atualizarSaldoContaCorrente((ContaCorrente) conta, novoSaldo);
        } else if (conta instanceof ContaPoupanca) {
            return atualizarSaldoContaPoupanca((ContaPoupanca) conta, novoSaldo);
        } else {
            throw new RuntimeException("Tipo de conta desconhecido");
        }
    }

    // Lógica específica para ContaCorrente
    private ContaCorrente atualizarSaldoContaCorrente(ContaCorrente contaCorrente, Double novoSaldo) {
        // Exemplo: adicionar um limite de crédito ao saldo da conta corrente
        double novoSaldoCorrente = novoSaldo + contaCorrente.getTaxa();  // Isso pode ser mais complexo conforme as regras
        contaCorrente.setSaldo(novoSaldoCorrente);
        return correnteRepository.save(contaCorrente);
    }

    // Lógica específica para ContaPoupanca
    private ContaPoupanca atualizarSaldoContaPoupanca(ContaPoupanca contaPoupanca, Double novoSaldo) {
        // Exemplo: aplicar rendimento ao saldo da conta poupança
        double novoSaldoPoupanca = novoSaldo + (novoSaldo * contaPoupanca.getRendimento());
        contaPoupanca.setSaldo(novoSaldoPoupanca);
        return poupancaRepository.save(contaPoupanca);
    }
}

