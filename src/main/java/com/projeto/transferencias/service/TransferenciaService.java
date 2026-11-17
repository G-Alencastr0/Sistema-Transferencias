package com.projeto.transferencias.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import com.projeto.transferencias.entity.Transferencia;
import com.projeto.transferencias.repository.ContaRepository;
import com.projeto.transferencias.repository.UsuarioRepository;
import com.projeto.transferencias.repository.TransferenciaRepository;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

        private final ContaRepository contaRepository;
        private final UsuarioRepository usuarioRepository;
        private final TransferenciaRepository transferenciaRepository;

        @Transactional(isolation = Isolation.READ_COMMITTED)
        public void transferir(String emailRemetente, Long destinatarioId, BigDecimal valor) {

                var remetente = usuarioRepository.findByEmail(emailRemetente)
                                .orElseThrow(() -> new IllegalArgumentException("Remetente não encontrado."));

                Long idRemetente = contaRepository.buscarIdPorUsuarioId(remetente.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Conta do remetente não encontrada."));

                if (destinatarioId == null) {
                        throw new IllegalArgumentException("O destinatário é obrigatório.");
                }

                if (idRemetente.equals(destinatarioId)) {
                        throw new IllegalArgumentException("Não é permitido transferir moedas para a própria conta.");
                }

                Long primeiroId = idRemetente.compareTo(destinatarioId) < 0
                                ? idRemetente
                                : destinatarioId;

                Long segundoId = idRemetente.compareTo(destinatarioId) < 0
                                ? destinatarioId
                                : idRemetente;

                var primeiraConta = contaRepository.buscarPorIdComBloqueio(primeiroId)
                                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

                var segundaConta = contaRepository.buscarPorIdComBloqueio(segundoId)
                                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

                var contaRemetente = idRemetente.equals(primeiroId)
                                ? primeiraConta
                                : segundaConta;

                var contaDestinatario = destinatarioId.equals(primeiroId)
                                ? primeiraConta
                                : segundaConta;

                if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
                }

                if (contaRemetente.getSaldo().compareTo(valor) < 0) {
                        throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência.");
                }

                LocalDateTime agora = LocalDateTime.now();

                boolean transferenciaDuplicada = transferenciaRepository
                                .existsByRemetenteAndDestinatarioAndValorAndDataHoraAfter(
                                                contaRemetente,
                                                contaDestinatario,
                                                valor,
                                                agora.minusSeconds(60));

                if (transferenciaDuplicada) {
                        throw new IllegalArgumentException(
                                        "Transferência duplicada. Aguarde 60 segundos para repetir a operação.");
                }

                // Desconta o valor da conta do remetente
                contaRemetente.setSaldo(
                                contaRemetente.getSaldo().subtract(valor));

                // Adiciona o valor à conta do destinatário
                contaDestinatario.setSaldo(
                                contaDestinatario.getSaldo().add(valor));

                Transferencia transferencia = new Transferencia();

                transferencia.setRemetente(contaRemetente);
                transferencia.setDestinatario(contaDestinatario);
                transferencia.setValor(valor);
                transferencia.setDataHora(agora);

                transferenciaRepository.save(transferencia);
        }
}
