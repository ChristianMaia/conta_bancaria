package com.example.conta_bancaria_mqtt.application.service;

import com.example.conta_bancaria_mqtt.application.dto.TaxaRegistroDTO;
import com.example.conta_bancaria_mqtt.application.dto.TaxaResponseDTO;
import com.example.conta_bancaria_mqtt.domain.entity.Taxa;
import com.example.conta_bancaria_mqtt.domain.exception.EntidadeNaoEncontradoException;
import com.example.conta_bancaria_mqtt.domain.repository.TaxaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxaService {

    private final TaxaRepository repository;


    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public TaxaResponseDTO registrarTaxa(TaxaRegistroDTO dto) {
        var novaTaxa = dto.toEntity();
        return TaxaResponseDTO.fromEntity(repository.save(novaTaxa));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<TaxaResponseDTO> listarTaxasAtivas() {
        return repository.findAllByAtivoTrue().stream()
                .map(TaxaResponseDTO::fromEntity)
                .toList();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','CLIENTE')")
    public TaxaResponseDTO buscarTaxaPorId(Long id) {
        Taxa taxa = acharTaxaPorId(id);
        return TaxaResponseDTO.fromEntity(taxa);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public TaxaResponseDTO atualizarTaxa(Long id, TaxaRegistroDTO dto) {
        Taxa taxa = acharTaxaPorId(id);

        taxa.setDescricao(dto.descricao());
        taxa.setPercentual(dto.percentual());
        taxa.setDescricao(dto.descricao());

        return TaxaResponseDTO.fromEntity(repository.save(taxa));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public void deletarTaxa(Long id) {
        Taxa taxa = acharTaxaPorId(id);
        repository.delete(taxa);
    }

    private Taxa acharTaxaPorId(Long id) {
        var taxa = repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntidadeNaoEncontradoException("taxa")
        );
        return taxa;
    }
}

