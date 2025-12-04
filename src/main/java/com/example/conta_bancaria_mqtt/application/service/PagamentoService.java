package com.example.conta_bancaria_mqtt.application.service;

import com.example.conta_bancaria_mqtt.application.dto.PagamentoRegistroDTO;
import com.example.conta_bancaria_mqtt.application.dto.PagamentoRequestDTO;
import com.example.conta_bancaria_mqtt.application.dto.PagamentoResponseDTO;
import com.example.conta_bancaria_mqtt.domain.entity.Cliente;
import com.example.conta_bancaria_mqtt.domain.entity.Conta;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.entity.Taxa;
import com.example.conta_bancaria_mqtt.domain.exception.EntidadeNaoEncontradoException;
import com.example.conta_bancaria_mqtt.domain.repository.CodigoAutenticacaoRepository;
import com.example.conta_bancaria_mqtt.domain.repository.ContaRepository;
import com.example.conta_bancaria_mqtt.domain.repository.PagamentoRepository;
import com.example.conta_bancaria_mqtt.domain.repository.TaxaRepository;
import com.example.conta_bancaria_mqtt.domain.service.PagamentoDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository repository;
    private final ContaRepository contaRepository;
    private final TaxaRepository taxaRepository;
    private final CodigoAutenticacaoRepository autenticacaoRepository;

    private Pagamento buscarPagamentoPorBoleto(String boleto) {
        var pagamento = repository.findPagamentoByBoleto(boleto).orElseThrow(
                () -> new EntidadeNaoEncontradoException("pagamento")
        );
        return pagamento;
    }

    @PreAuthorize("hasAnyRole('GERENTE')")
    public PagamentoResponseDTO verPagamento(String boleto) {
        var pagamento = buscarPagamentoPorBoleto(boleto);
        return PagamentoResponseDTO.fromEntity(pagamento);
    }

    @PreAuthorize("hasRole('GERENTE')")
    public List<PagamentoResponseDTO> listarPagamentos() {
        return repository.findAll().stream()
                .map(PagamentoResponseDTO::fromEntity)
                .toList();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    public PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO dto) {
        Conta conta = contaRepository.findByNumeroAndAtivaTrue(dto.contaNumero())
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Conta não encontrada"));

        // para validar o codigo do IoT
        //autenticacaoRepository.validarPagamento(dto.codigoAutenticacao(), conta.getCliente().getCpf());

        // busca o tipo de taxas disponíveis no banco, para determinado pagamento
        List<Taxa> taxasDoBanco = taxaRepository.findByTipoPagamento(dto.tipoPagamento());

        Pagamento pagamento = dto.toEntity(conta);
        PagamentoDomainService.validarPagamento(pagamento);
        // calculo das taxas do banco
        PagamentoDomainService.calcularTaxa(pagamento, taxasDoBanco);
        PagamentoDomainService.definirStatus(pagamento, true); // se chegou até aqui, IoT validado

        Pagamento pagamentoSalvo = repository.save(pagamento);
        return PagamentoResponseDTO.fromEntity(pagamentoSalvo);
    }
}