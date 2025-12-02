package com.example.conta_bancaria_mqtt.application.service;

import com.example.conta_bancaria_mqtt.application.dto.PagamentoRegistroDTO;
import com.example.conta_bancaria_mqtt.application.dto.PagamentoResponseDTO;
import com.example.conta_bancaria_mqtt.domain.entity.Cliente;
import com.example.conta_bancaria_mqtt.domain.entity.Pagamento;
import com.example.conta_bancaria_mqtt.domain.exception.EntidadeNaoEncontradoException;
import com.example.conta_bancaria_mqtt.domain.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository repository;

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public PagamentoResponseDTO registrarPagamento(PagamentoRegistroDTO pagamento){
        var novoPagamento = pagamento.toEntity();
        return PagamentoResponseDTO.fromEntity(repository.save(novoPagamento));
    }

    public PagamentoResponseDTO atualizarPagamento(String boleto, PagamentoRegistroDTO dto){
        Pagamento pagamento = buscarPagamentoPorBoleto(boleto);

        pagamento.setValorPago(dto.valorPago());

        return PagamentoResponseDTO.fromEntity(repository.save(pagamento));
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE', 'CLIENTE')")
    private Pagamento buscarPagamentoPorBoleto(String boleto) {
        var pagamento = repository.findPagamentoByBoleto(boleto).orElseThrow(
                () -> new EntidadeNaoEncontradoException("pagamento")
        );
        return pagamento;
    }


}
