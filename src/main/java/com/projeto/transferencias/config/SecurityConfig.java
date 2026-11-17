package com.projeto.transferencias.config;

import com.projeto.transferencias.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http,
                        JwtService jwtService) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())

                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                                        response.setContentType("application/json");
                                                        response.getWriter().write("{\"error\":\"NAO_AUTENTICADO\"}");
                                                }))

                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/auth/registrar",
                                                                "/auth/login",
                                                                "/auditoria",
                                                                "/error")
                                                .permitAll()

                                                .anyRequest().authenticated())

                                .addFilterBefore(
                                                new JwtAuthenticationFilter(jwtService),
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
