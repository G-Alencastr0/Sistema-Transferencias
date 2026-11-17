package com.projeto.transferencias.controller;

import com.projeto.transferencias.dto.ContaResponse;
import com.projeto.transferencias.repository.ContaRepository;
import com.projeto.transferencias.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaController {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<ContaResponse> consultarConta(Authentication authentication) {

        String email = authentication.getName();

        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        var conta = contaRepository.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        ContaResponse resposta = new ContaResponse(
                conta.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                conta.getSaldo());

        return ResponseEntity.ok(resposta);
    }

}
