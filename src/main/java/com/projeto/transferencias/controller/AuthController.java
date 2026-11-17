package com.projeto.transferencias.controller;

import com.projeto.transferencias.dto.LoginRequest;
import com.projeto.transferencias.dto.LoginResponse;
import com.projeto.transferencias.dto.CadastroRequest;
import com.projeto.transferencias.dto.UsuarioResponse;
import com.projeto.transferencias.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // informa ao Spring que essa classe receberá requisições HTTP e devolverá
                // respostas, geralmente em JSON
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> registrar(
            @Valid @RequestBody CadastroRequest request) {

        UsuarioResponse resposta = usuarioService.cadastrar(
                request.getNome(),
                request.getEmail(),
                request.getSenha());

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse resposta = usuarioService.login(
                request.getEmail(),
                request.getSenha());

        return ResponseEntity.ok(resposta);
    }

}
