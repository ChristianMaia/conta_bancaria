package com.example.conta_bancaria_mqtt.domain.repository;

import com.example.conta_bancaria_mqtt.domain.entity.Cliente;
import com.example.conta_bancaria_mqtt.domain.entity.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaxaRepository extends JpaRepository<Taxa, Long> {

}
