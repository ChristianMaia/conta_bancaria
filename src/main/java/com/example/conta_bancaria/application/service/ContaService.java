package com.example.conta_bancaria.application.service;

import com.example.conta_bancaria.application.dto.ContaAtualizacaoDTO;
import com.example.conta_bancaria.application.dto.ContaResumoDTO;
import com.example.conta_bancaria.application.dto.TransferenciaDTO;
import com.example.conta_bancaria.application.dto.ValorSaqueDepositoDTO;
import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.exception.EntidadeNaoEncontradoException;
import com.example.conta_bancaria.domain.exception.RendimentoInvalidoException;
import com.example.conta_bancaria.domain.exception.TipoDeContaInvalidoException;
import com.example.conta_bancaria.domain.repository.ContaRepository;
import com.example.conta_bancaria.domain.repository.CorrenteRepository;
import com.example.conta_bancaria.domain.repository.PoupancaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ContaService {
    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivaTrue().stream()
                .filter(Conta::isAtiva)
                .map(ContaResumoDTO::FromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.FromEntity(
                buscarContaAtivaPorNumero(numero)
        );
    }

    public ContaResumoDTO atualizarConta(String numero, ContaAtualizacaoDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numero);
        if (conta instanceof ContaPoupanca poupanca){
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        } else {
            throw new TipoDeContaInvalidoException("");
        }
        conta.setSaldo(dto.saldo());
        return ContaResumoDTO.FromEntity(repository.save(conta));
    }

    public void deletarConta(String numero){
        Conta conta = buscarContaAtivaPorNumero(numero);
        conta.setAtiva(false);
        repository.save(conta);
    }

    public ContaResumoDTO sacar(String numero, ValorSaqueDepositoDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numero);
        conta.sacar(dto.valor());
        return ContaResumoDTO.FromEntity(repository.save(conta));
    }

    public ContaResumoDTO depositar(String numero, ValorSaqueDepositoDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numero);
        conta.depositar(dto.valor());
        return ContaResumoDTO.FromEntity(repository.save(conta));
    }


    public ContaResumoDTO transferir(String numero, TransferenciaDTO dto) {
        Conta contaOrigem = buscarContaAtivaPorNumero(numero);
        Conta contaDestino = buscarContaAtivaPorNumero(dto.contaDestino());

        contaOrigem.transferir(dto.valor(), contaDestino);

        repository.save(contaDestino);
        return ContaResumoDTO.FromEntity(repository.save(contaOrigem));
    }

    public ContaResumoDTO aplicarRendimento(String numeroDaConta) {
        Conta conta = buscarContaAtivaPorNumero(numeroDaConta);
        if (conta instanceof ContaPoupanca poupanca){
            poupanca.aplicarRendimento();
            return ContaResumoDTO.FromEntity(repository.save(poupanca));
        }
        throw new RendimentoInvalidoException();
    }

    private Conta buscarContaAtivaPorNumero(String numero){
        return repository.findByNumeroAndAtivaTrue(numero)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("conta"));
    }

}

