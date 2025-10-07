package com.example.conta_bancaria.application.service;

import com.example.conta_bancaria.application.dto.ClienteAtualizadoDTO;
import com.example.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.example.conta_bancaria.application.dto.ClienteResponseDTO;
import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;
import com.example.conta_bancaria.domain.exception.ContaMesmoTipoException;
import com.example.conta_bancaria.domain.exception.EntidadeNaoEncontradoException;
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
            throw new ContaMesmoTipoException();

        cliente.getContas().add(novaConta);

        return ClienteResponseDTO.fromEntity(repository.save(cliente));

    }

    public List<ClienteResponseDTO> listarClientesAtivos(){
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity).toList();
    }

    public ClienteResponseDTO buscarClientesPorCPF(String cpf){
        var cliente = buscarClientePorCpfAtivo(cpf);
        return ClienteResponseDTO.fromEntity(cliente);
    }


    public ClienteResponseDTO atualizarCliente(String cpf, ClienteAtualizadoDTO dto) {
        var cliente = buscarClientePorCpfAtivo(cpf);

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

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