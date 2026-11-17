package com.projeto.transferencias.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaRequest {

    @NotNull(message = "O destinatário é obrigatório.")
    private Long destinatarioId;

    @NotNull(message = "O valor é obrigatório.")
    @Positive(message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

}
