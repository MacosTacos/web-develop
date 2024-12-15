package org.web.dev.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.web.dev.domain.enums.UserRole;
import org.web.dev.repositories.UserRepository;
import org.web.dev.services.impl.AppUserDetailsService;

@Configuration
public class AppSecurityConfiguration {
    private UserRepository userRepository;

    public AppSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests.
                                        requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        .requestMatchers("/favicon.ico").permitAll()
                                        .requestMatchers("/error").permitAll()
                                        .requestMatchers("/", "/users/login", "/users/register", "/users/login-error")
                                        .permitAll().
                                        requestMatchers("books/delete", "books/update", "books/create").hasRole(UserRole.ADMIN.name()).
                                        requestMatchers("authors/delete", "authors/update", "authors/create", "authors").hasRole(UserRole.ADMIN.name()).
                                        requestMatchers("genres/delete", "genres/update", "genres/create", "genres").hasRole(UserRole.ADMIN.name()).
                                        requestMatchers("cart/add", "card/clear", "cart/remove", "cart").authenticated().
                                        requestMatchers("orders/create", "orders/").authenticated().
                                        anyRequest().permitAll()
                )
                .formLogin(
                        (formLogin) ->
                                formLogin.
                                        loginPage("/users/login").
                                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                                        defaultSuccessUrl("/books/genre").
                                        failureForwardUrl("/users/login-error")
                )
                .logout((logout) ->
                        logout.logoutUrl("/users/logout").
                                logoutSuccessUrl("/books/genre").
                                invalidateHttpSession(true)
                ).securityContext(
                        securityContext -> securityContext.
                                securityContextRepository(securityContextRepository)
                ).csrf().disable();


        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() { return new AppUserDetailsService(userRepository); }
}
