package com.projeto.transferencias.controller;

import com.projeto.transferencias.dto.AuditoriaResponse;
import com.projeto.transferencias.service.AuditoriaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<AuditoriaResponse> consultarAuditoria() {

        AuditoriaResponse auditoria = auditoriaService.consultarAuditoria();

        return ResponseEntity.ok(auditoria);
    }
}
