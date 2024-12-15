package org.web.dev.controllers;

import jakarta.validation.Valid;
import org.example.dtos.auth.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.web.dev.services.impl.AuthService;

@Controller
@RequestMapping("/users")
public class AuthControllerImpl {
    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String register() {
        return "auth/registration.html";
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationForm form, BindingResult bindingResult) {
        authService.register(form);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login.html";
    }


}
