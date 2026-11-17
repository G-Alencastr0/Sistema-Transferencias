package com.projeto.transferencias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditoriaResponse {

    private Long usuarios;
    private Long somaSaldos;
    private Long transferencias;
}
