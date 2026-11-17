package com.projeto.transferencias.controller;

import com.projeto.transferencias.dto.AuditoriaResponse;
import com.projeto.transferencias.service.AuditoriaService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<List<AuditoriaResponse>> consultarAuditoria() {

        List<AuditoriaResponse> auditoria = auditoriaService.consultarAuditoria();

        return ResponseEntity.ok(auditoria);
    }
}
