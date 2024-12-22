package org.web.dev.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.AuthController;
import org.example.dtos.auth.RegistrationForm;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.web.dev.services.impl.AuthService;

@Controller
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String register(Model model) {
        LOG.info("GET:/users/register Registration page requested");
        model.addAttribute("form", new RegistrationForm(null, null, null, null));
        return "auth/registration.html";
    }

    @Override
    public String register(RegistrationForm form, BindingResult bindingResult, Model model) {
        LOG.info("POST:/users/register Registration request");
        if (bindingResult.hasErrors()) {
            return "auth/registration.html";
        }
        authService.register(form);
        return "redirect:/users/login";
    }

    @Override
    public String login() {
        LOG.info("GET:/users/login Login page requested");
        return "auth/login.html";
    }

    @Override
    public String onFailedLogin(
            String username,
            RedirectAttributes redirectAttributes) {
        LOG.info("POST:/users/login-error Login failed for username " + username);

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }


}
