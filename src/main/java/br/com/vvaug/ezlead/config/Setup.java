package br.com.vvaug.ezlead.config;

import br.com.vvaug.ezlead.document.Project;
import br.com.vvaug.ezlead.document.Role;
import br.com.vvaug.ezlead.document.User;
import br.com.vvaug.ezlead.repository.ProjectRepository;
import br.com.vvaug.ezlead.repository.RoleRepository;
import br.com.vvaug.ezlead.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Setup {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner init() {
        return (args) -> {

            roleRepository.deleteAll();
            userRepository.deleteAll();
            projectRepository.deleteAll();
            
            var role = roleRepository.save(
                    Role.builder()
                            .name("ADMIN_ROLE")
                            .build()
            );

            var project = Project.builder()
                    .name("Projeto 1")
                    .keyword("XYZ")
                    .city("São Bernardo do Campo")
                    .state("São Paulo")
                    .neighbourhood("Dos Casas")
                    .place("Xpto")
                    .build();

         projectRepository.save(project);

         userRepository.save(
                    User.builder()
                            .email("admin@admin")
                            .name("ADMIN")
                            .password(passwordEncoder.encode("123456"))
                            .phone("11987634581")
                            .roles(Collections.singletonList(
                                    role
                            ))
                            .projects(List.of(project))
                            .build()
            );
        };
    }
}
