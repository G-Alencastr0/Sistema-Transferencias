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
    public UsuarioResponse cadastrar(String username, String password) {

        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("USUARIO_EXISTENTE");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));

        usuario = usuarioRepository.save(usuario);

        Conta conta = new Conta();
        conta.setUsuario(usuario);
        conta.setSaldo(new BigDecimal("1000"));

        contaRepository.save(conta);

        return new UsuarioResponse(usuario.getUsername());
    }

    public LoginResponse login(String username, String password) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("CREDENCIAIS_INVALIDAS"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new IllegalArgumentException("CREDENCIAIS_INVALIDAS");
        }

        String token = jwtService.gerarToken(usuario.getUsername());

        return new LoginResponse(token);
    }
}
