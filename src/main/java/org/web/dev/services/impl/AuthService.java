package org.web.dev.services.impl;

import org.example.dtos.auth.LoginForm;
import org.example.dtos.auth.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.web.dev.domain.entities.RoleEntity;
import org.web.dev.domain.entities.UserEntity;
import org.web.dev.domain.enums.UserRole;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.exceptions.user.UserEmailException;
import org.web.dev.exceptions.user.UserNameException;
import org.web.dev.exceptions.user.UserPasswordException;
import org.web.dev.repositories.RoleRepository;
import org.web.dev.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegistrationForm form) {

        if (!form.password().equals(form.confirmPassword())) {
            throw new UserPasswordException("passwords do not match");
        }
        Optional<UserEntity> userEntityCheck = userRepository.findByEmail(form.email());
        if (userEntityCheck.isPresent()) {
            throw new UserEmailException("user with email " + form.email() + " already exists");
        }
        userEntityCheck = userRepository.findByName(form.email());
        if (userEntityCheck.isPresent()) {
            throw new UserNameException("user with username " + form.name() + " already exists");
        }

        RoleEntity role = roleRepository.findRoleByName(UserRole.USER)
                .orElseThrow(() -> new ResourceNotFoundException("role not found"));

        UserEntity userEntity = new UserEntity(
                form.name(),
                form.email(),
                passwordEncoder.encode(form.password())
        );
        userEntity.setRoles(List.of(role));
        userRepository.save(userEntity);
    }

}
