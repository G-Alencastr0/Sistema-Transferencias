package com.projeto.transferencias.controller;

import com.projeto.transferencias.dto.TransferenciaRequest;
import com.projeto.transferencias.service.TransferenciaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<Void> transferir(
            @Valid @RequestBody TransferenciaRequest request,
            Authentication authentication) {

        String emailRemetente = authentication.getName();

        transferenciaService.transferir(
                emailRemetente,
                request.getDestinatarioId(),
                request.getValor());

        return ResponseEntity.ok().build();
    }
}
