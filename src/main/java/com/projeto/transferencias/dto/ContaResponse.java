package com.projeto.transferencias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ContaResponse {

    private Long id;
    private String nome;
    private String email;
    private BigDecimal saldo;

}
