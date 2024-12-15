package org.web.dev.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.web.dev.domain.entities.RoleEntity;
import org.web.dev.domain.entities.UserEntity;
import org.web.dev.domain.enums.UserRole;
import org.web.dev.repositories.*;

import java.util.List;

@Component
public class Clr implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Clr(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findRoleByName(UserRole.USER).isEmpty()) {
            roleRepository.save(new RoleEntity(UserRole.USER));
        }
        if (roleRepository.findRoleByName(UserRole.ADMIN).isEmpty()) {
            roleRepository.save(new RoleEntity(UserRole.ADMIN));
        }
        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
            RoleEntity roleEntity = roleRepository.findRoleByName(UserRole.ADMIN)
                    .orElseThrow();
            userRepository.save(new UserEntity("admin", "admin@admin.com",  passwordEncoder.encode("admin"), List.of(roleEntity)));
        }
    }
}
