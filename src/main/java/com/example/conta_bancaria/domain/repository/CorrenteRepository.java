package com.example.conta_bancaria.domain.repository;

import com.example.conta_bancaria.domain.entity.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrenteRepository extends JpaRepository<ContaCorrente, Long> {
}
