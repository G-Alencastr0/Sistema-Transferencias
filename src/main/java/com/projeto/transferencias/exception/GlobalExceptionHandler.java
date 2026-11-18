package com.projeto.transferencias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, String>> tratarErroDeRegra(
                        IllegalArgumentException ex) {

                String codigo = ex.getMessage();

                HttpStatus status = switch (codigo) {
                        case "VALOR_INVALIDO", "MESMA_CONTA" ->
                                HttpStatus.UNPROCESSABLE_ENTITY;

                        case "DESTINO_INEXISTENTE" ->
                                HttpStatus.NOT_FOUND;

                        case "TRANSFERENCIA_DUPLICADA", "SALDO_INSUFICIENTE",
                                        "USUARIO_EXISTENTE" ->
                                HttpStatus.CONFLICT;

                        case "CREDENCIAIS_INVALIDAS" ->
                                HttpStatus.UNAUTHORIZED;

                        default ->
                                HttpStatus.BAD_REQUEST;
                };

                return ResponseEntity
                                .status(status)
                                .body(Map.of("error", codigo));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> tratarErroDeValidacao(
                        MethodArgumentNotValidException ex) {

                return ResponseEntity
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .body(Map.of("error", "VALOR_INVALIDO"));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, String>> tratarErroDeLeituraJson(
                        HttpMessageNotReadableException ex) {

                return ResponseEntity
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .body(Map.of("error", "VALOR_INVALIDO"));
        }
}
