package com.example.conta_bancaria_mqtt.application.service;

import com.example.conta_bancaria_mqtt.application.dto.ClienteResponseDTO;
import com.example.conta_bancaria_mqtt.application.dto.GerenteAtualizacaoDTO;
import com.example.conta_bancaria_mqtt.application.dto.GerenteRegistroDTO;
import com.example.conta_bancaria_mqtt.application.dto.GerenteResponseDTO;
import com.example.conta_bancaria_mqtt.domain.entity.Gerente;
import com.example.conta_bancaria_mqtt.domain.exception.EntidadeNaoEncontradoException;
import com.example.conta_bancaria_mqtt.domain.repository.GerenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GerenteService {
    private final GerenteRepository repository;
    private final PasswordEncoder encoder;

    public GerenteResponseDTO registrarGerente(GerenteRegistroDTO dto) {
        Gerente gerenteRegistrado = repository // verifica se o gerente já existe
                .findByCpfAndAtivoTrue(dto.cpf())
                .orElseGet( // se não existir, cria um novo
                        () -> repository.save(dto.toEntity())
                );

        gerenteRegistrado.setSenha(encoder.encode(dto.senha()));
        return GerenteResponseDTO.fromEntity(repository.save(gerenteRegistrado));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public GerenteResponseDTO buscarGerente(String cpf) {
        return GerenteResponseDTO.fromEntity(procurarGerenteAtivo(cpf));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public List<GerenteResponseDTO> listarTodosOsGerentes() {
        return repository.findAllByAtivoTrue().stream()
                .map(GerenteResponseDTO::fromEntity).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public GerenteResponseDTO atualizarGerente(String cpf, GerenteAtualizacaoDTO dto) {
        Gerente gerente = procurarGerenteAtivo(cpf);

        gerente.setNome(dto.nome());
        gerente.setCpf(dto.cpf());
        gerente.setSenha(dto.senha());

        return GerenteResponseDTO.fromEntity(repository.save(gerente));
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    public void apagarGerente(String cpf) {
        Gerente gerente = procurarGerenteAtivo(cpf);

        gerente.setAtivo(false);

        repository.save(gerente);
    }

    private Gerente procurarGerenteAtivo(String cpf) {
        return repository
                .findByCpfAndAtivoTrue(cpf)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("gerente"));
    }
}
