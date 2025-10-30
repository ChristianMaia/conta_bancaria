package com.example.conta_bancaria_mqtt.domain.repository;

import com.example.conta_bancaria_mqtt.domain.entity.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GerenteRepository extends JpaRepository<Gerente, Long> {
    Optional<Gerente> findByCpfAndAtivoTrue(String cpf);
    Optional<Gerente> findByCpf(String cpf);
}
