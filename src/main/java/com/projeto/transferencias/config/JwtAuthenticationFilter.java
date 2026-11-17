package com.projeto.transferencias.config;

import com.projeto.transferencias.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            try {
                String email = jwtService.extrairEmail(token);

                var autenticacao = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        Collections.emptyList());

                SecurityContextHolder.getContext()
                        .setAuthentication(autenticacao);

            } catch (JwtException | IllegalArgumentException ex) {
                System.out.println("ERRO AO VALIDAR JWT: " + ex.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
