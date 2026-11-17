package com.projeto.transferencias.service;

import com.projeto.transferencias.dto.AuditoriaResponse;
import com.projeto.transferencias.repository.ContaRepository;
import com.projeto.transferencias.repository.UsuarioRepository;
import com.projeto.transferencias.repository.TransferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;
    private final TransferenciaRepository transferenciaRepository;

    @Transactional(readOnly = true)
    public AuditoriaResponse consultarAuditoria() {

        Long usuarios = usuarioRepository.count();

        Long somaSaldos = contaRepository.somarSaldos().longValueExact();

        Long transferencias = transferenciaRepository.count();

        return new AuditoriaResponse(usuarios, somaSaldos, transferencias);
    }
}
