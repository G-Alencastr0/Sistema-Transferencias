package com.projeto.transferencias.service;

import com.projeto.transferencias.dto.LoginResponse;
import com.projeto.transferencias.dto.UsuarioResponse;
import com.projeto.transferencias.entity.Conta;
import com.projeto.transferencias.entity.Usuario;
import com.projeto.transferencias.repository.ContaRepository;
import com.projeto.transferencias.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public UsuarioResponse cadastrar(String nome, String email, String senha) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));

        usuario = usuarioRepository.save(usuario);

        Conta conta = new Conta();
        conta.setUsuario(usuario);
        conta.setSaldo(new BigDecimal("1000.00"));

        contaRepository.save(conta);

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());

    }

    public LoginResponse login(String email, String senha) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos."));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new IllegalArgumentException("E-mail ou senha inválidos.");
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());
    }

}
