package com.example.conta_bancaria_mqtt.domain.repository;

import com.example.conta_bancaria_mqtt.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpfAndAtivoTrue(String cpf);
}
