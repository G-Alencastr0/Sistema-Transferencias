package com.projeto.transferencias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuditoriaResponse {

    private Long id;
    private String remetente;
    private String destinatario;
    private BigDecimal valor;
    private LocalDateTime dataHora;
}
