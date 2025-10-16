package com.example.conta_bancaria.application.service;

import com.example.conta_bancaria.application.dto.AuthDTO;
import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.entity.Gerente;
import com.example.conta_bancaria.domain.exception.EntidadeNaoEncontradoException;
import com.example.conta_bancaria.domain.repository.ClienteRepository;
import com.example.conta_bancaria.domain.repository.GerenteRepository;
import com.example.conta_bancaria.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final GerenteRepository gerentes;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String login(AuthDTO.LoginRequest req) {
        Gerente gerente = gerentes.findByCpfAndAtivoTrue(req.cpf())
                .orElseThrow(() ->  new EntidadeNaoEncontradoException("cliente"));

        if (!encoder.matches(req.senha(), gerente.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        return jwt.generateToken(gerente.getCpf(), gerente.getRole().name());
    }
}
