package com.example.conta_bancaria.application.service;

import com.example.conta_bancaria.application.dto.ClienteDTO;
import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.entity.Conta;
import com.example.conta_bancaria.domain.entity.ContaCorrente;
import com.example.conta_bancaria.domain.entity.ContaPoupanca;
import com.example.conta_bancaria.domain.repository.ClienteRepository;
import com.example.conta_bancaria.domain.repository.CorrenteRepository;
import com.example.conta_bancaria.domain.repository.PoupancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {
   @Autowired
    private ClienteRepository clienteRepository;
   @Autowired
    private PoupancaRepository poupancaRepository;
   @Autowired
    private CorrenteRepository correnteRepository;

    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteDTO::fromEntity)
                .toList();
    }

    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        var cliente = clienteDTO.toEntity();
        var clienteSalvo = clienteRepository.save(cliente);
        return ClienteDTO.fromEntity(clienteSalvo);
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO dto) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));

        // Atualiza nome e cpf se precisar
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        // Supondo que o DTO traga a lista de contas com os saldos atualizados
        for (var contaDto : dto.contas()) {
            var conta = cliente.getContas()
                    .stream()
                    .filter(c -> c.getId().equals(contaDto.id()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + contaDto.id()));

            conta.setSaldo(contaDto.saldo());
        }

        var clienteAtualizado = clienteRepository.save(cliente);
        return ClienteDTO.fromEntity(clienteAtualizado);
    }

    public void sacar(Long clienteId, String numeroConta, Double valor) {
        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + clienteId));

    }
    public void deletarCliente(Long id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));

        cliente.getContas().forEach(conta -> {
            if (conta instanceof ContaCorrente) {
                correnteRepository.deleteById(conta.getId());
            } else if (conta instanceof ContaPoupanca) {
                poupancaRepository.deleteById(conta.getId());
            }
        });
        clienteRepository.deleteById(id);
    }

}
