package com.projeto.transferencias.service;

import com.projeto.transferencias.dto.AuditoriaResponse;
import com.projeto.transferencias.repository.TransferenciaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final TransferenciaRepository transferenciaRepository;

    @Transactional(readOnly = true)
    public List<AuditoriaResponse> consultarAuditoria() {

        return transferenciaRepository.findAllByOrderByDataHoraDesc()
                .stream()
                .map(transferencia -> new AuditoriaResponse(
                        transferencia.getId(),
                        transferencia.getRemetente().getUsuario().getNome(),
                        transferencia.getDestinatario().getUsuario().getNome(),
                        transferencia.getValor(),
                        transferencia.getDataHora()))
                .toList();
    }
}
