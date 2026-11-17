package com.projeto.transferencias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Positive
    private Integer valor;

}
