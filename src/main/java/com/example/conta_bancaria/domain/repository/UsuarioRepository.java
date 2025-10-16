package com.example.conta_bancaria.domain.repository;

import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpfAndAtivoTrue(String cpf);
}
