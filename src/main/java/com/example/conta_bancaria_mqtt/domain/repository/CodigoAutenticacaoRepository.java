package com.example.conta_bancaria_mqtt.domain.repository;

import com.example.conta_bancaria_mqtt.domain.entity.Cliente;
import com.example.conta_bancaria_mqtt.domain.entity.CodigoAutenticacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoAutenticacaoRepository extends JpaRepository<CodigoAutenticacao, Long> {
    Optional<CodigoAutenticacao> findTopByClienteOrderByExpiraEmDesc(Cliente cliente);
}
