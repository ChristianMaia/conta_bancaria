package com.example.conta_bancaria.application.service;

import com.example.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.example.conta_bancaria.application.dto.ClienteResponseDTO;
import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;
import com.example.conta_bancaria.domain.repository.ClienteRepository;
import com.example.conta_bancaria.domain.repository.CorrenteRepository;
import com.example.conta_bancaria.domain.repository.PoupancaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

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
            throw new RuntimeException("Cliente ja possui uma conta desse tipo");

        cliente.getContas().add(novaConta);

        return ClienteResponseDTO.fromEntity(repository.save(cliente));

    }

    public List<ClienteResponseDTO> listarClientesAtivos(){
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity).toList();
    }

    public ClienteResponseDTO listarClientesPorCPF(String cpf){
        var cliente = repository.findByCpfAndAtivoTrue(cpf).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );
        return ClienteResponseDTO.fromEntity(cliente);
    }
}