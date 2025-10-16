package com.example.conta_bancaria.infrastructure.config;

import com.example.conta_bancaria.domain.entity.Gerente;
import com.example.conta_bancaria.domain.enums.Role;
import com.example.conta_bancaria.domain.repository.ClienteRepository;
import com.example.conta_bancaria.domain.repository.GerenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    private final GerenteRepository gerenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sistema.gerente.cpf}")
    private String gerenteCpf;

    @Value("${sistema.gerente.senha}")
    private String gerenteSenha;

    @Override
    public void run(String... args) {
        gerenteRepository.findByCpfAndAtivoTrue(gerenteCpf).ifPresentOrElse(
                gerentes -> {
                    if (!gerentes.isAtivo()) {
                        gerentes.setAtivo(true);
                        gerenteRepository.save(gerentes);
                    }
                },
                () -> {
                    Gerente admin = Gerente.builder()
                            .nome("Gerente provisorio")
                            .cpf(gerenteCpf)
                            .senha(passwordEncoder.encode(gerenteSenha))
                            .role(Role.GERENTE)
                            .build();
                    gerenteRepository.save(admin);
                    System.out.println("⚡ Usuário admin provisório criado: " + gerenteCpf);
                }
        );
    }
}
