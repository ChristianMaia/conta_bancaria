package com.example.conta_bancaria_mqtt.domain.repository;

import com.example.conta_bancaria_mqtt.domain.entity.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrenteRepository extends JpaRepository<ContaCorrente, Long> {
}
