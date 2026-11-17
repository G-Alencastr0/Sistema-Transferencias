package com.projeto.transferencias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransferenciaResponse {

    private Long id;
    private String origem;
    private String destino;
    private Integer valor;
    private Integer saldo;
}
