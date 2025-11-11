package com.example.conta_bancaria_mqtt.application.service;

import com.example.conta_bancaria_mqtt.application.dto.ClienteAtualizadoDTO;
import com.example.conta_bancaria_mqtt.application.dto.ClienteRegistroDTO;
import com.example.conta_bancaria_mqtt.application.dto.ClienteResponseDTO;
import com.example.conta_bancaria_mqtt.domain.entity.Cliente;
import com.example.conta_bancaria_mqtt.domain.exception.ContaMesmoTipoException;
import com.example.conta_bancaria_mqtt.domain.exception.EntidadeNaoEncontradoException;
import com.example.conta_bancaria_mqtt.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ClienteResponseDTO registrarCliente(ClienteRegistroDTO dto){
        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
                () -> repository.save(dto.toEntity())
        );
        var conta = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = conta.stream().anyMatch(
                c -> c.getClass().equals(novaConta.getClass()) && c.isAtiva()
        );
        if (jaTemTipo)
            throw new ContaMesmoTipoException();

        cliente.getContas().add(novaConta);
        cliente.setSenha(passwordEncoder.encode(dto.senha()));

        return ClienteResponseDTO.fromEntity(repository.save(cliente));

    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<ClienteResponseDTO> listarClientesAtivos(){
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity).toList();
    }
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ClienteResponseDTO buscarClientesPorCPF(String cpf){
        var cliente = buscarClientePorCpfAtivo(cpf);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ClienteResponseDTO atualizarCliente(String cpf, ClienteAtualizadoDTO dto) {
        var cliente = buscarClientePorCpfAtivo(cpf);

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public void deletarCliente(String cpf){
        var cliente = buscarClientePorCpfAtivo(cpf);
        cliente.setAtivo(false);

        cliente.getContas().forEach(
                conta -> conta.setAtiva(false)
        );
        repository.save(cliente);
    }
    private Cliente buscarClientePorCpfAtivo(String cpf) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf).orElseThrow(
                () -> new EntidadeNaoEncontradoException("cliente")
        );
        return cliente;
    }
    
}