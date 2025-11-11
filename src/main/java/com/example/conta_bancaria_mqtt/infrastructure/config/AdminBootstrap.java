package com.example.conta_bancaria_mqtt.infrastructure.config;

import com.example.conta_bancaria_mqtt.domain.entity.Gerente;
import com.example.conta_bancaria_mqtt.domain.enums.Role;
import com.example.conta_bancaria_mqtt.domain.repository.GerenteRepository;
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

    @Value("${sistema.admin.cpf}")
    private String adminCpf;

    @Value("${sistema.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) {
        gerenteRepository.findByCpfAndAtivoTrue(adminCpf).ifPresentOrElse(
                gerentes -> {
                    if (!gerentes.isAtivo()) {
                        gerentes.setAtivo(true);
                        gerenteRepository.save(gerentes);
                    }
                },
                () -> {
                    Gerente admin = Gerente.builder()
                            .nome("Administrador provisório")
                            .cpf(adminCpf)
                            .senha(passwordEncoder.encode(adminSenha))
                            .role(Role.ADMIN)
                            .ativo(true)
                            .build();
                    gerenteRepository.save(admin);
                    System.out.println("⚡ Usuário admin provisório criado: " + adminCpf);
                }
        );
    }
}
