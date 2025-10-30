package com.example.conta_bancaria_mqtt.domain.repository;

import com.example.conta_bancaria_mqtt.domain.entity.ContaPoupanca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoupancaRepository extends JpaRepository<ContaPoupanca, Long> {
}
