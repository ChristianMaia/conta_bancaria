package com.example.conta_bancaria.infrastructure.security;

import com.example.conta_bancaria.domain.repository.GerenteRepository;
import com.example.conta_bancaria.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService {

    private final UsuarioRepository repository;

    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        var usuario = repository.findByCpfAndAtivoTrue(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(
                usuario.getCpf(),
                usuario.getSenha(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()))
        );
    }
}
