package com.example.conta_bancaria.domain.repository;

import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.entity.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GerenteRepository extends JpaRepository<Gerente, Long> {
    Optional<Gerente> findByCpfAndAtivoTrue(String cpf);
}
