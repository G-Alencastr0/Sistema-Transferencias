
package com.projeto.transferencias.service;

import com.projeto.transferencias.dto.TransferenciaResponse;
import com.projeto.transferencias.entity.Transferencia;
import com.projeto.transferencias.repository.ContaRepository;
import com.projeto.transferencias.repository.UsuarioRepository;
import com.projeto.transferencias.repository.TransferenciaRepository;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

        private final ContaRepository contaRepository;
        private final UsuarioRepository usuarioRepository;
        private final TransferenciaRepository transferenciaRepository;

        @Transactional(isolation = Isolation.READ_COMMITTED)
        public TransferenciaResponse transferir(
                        String usernameRemetente,
                        String usernameDestinatario,
                        Integer valor) {

                if (valor == null || valor <= 0) {
                        throw new IllegalArgumentException("VALOR_INVALIDO");
                }

                var remetente = usuarioRepository.findByUsername(usernameRemetente)
                                .orElseThrow(() -> new IllegalArgumentException("Remetente não encontrado."));

                var destinatario = usuarioRepository.findByUsername(usernameDestinatario)
                                .orElseThrow(() -> new IllegalArgumentException("DESTINO_INEXISTENTE"));

                Long idRemetente = contaRepository.buscarIdPorUsuarioId(remetente.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Conta do remetente não encontrada."));

                Long idDestinatario = contaRepository.buscarIdPorUsuarioId(destinatario.getId())
                                .orElseThrow(() -> new IllegalArgumentException("DESTINO_INEXISTENTE"));

                if (idRemetente.equals(idDestinatario)) {
                        throw new IllegalArgumentException("MESMA_CONTA");
                }

                // Bloqueia as contas sempre na mesma ordem para evitar deadlocks.
                Long primeiroId = idRemetente.compareTo(idDestinatario) < 0
                                ? idRemetente
                                : idDestinatario;

                Long segundoId = idRemetente.compareTo(idDestinatario) < 0
                                ? idDestinatario
                                : idRemetente;

                var primeiraConta = contaRepository.buscarPorIdComBloqueio(primeiroId)
                                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

                var segundaConta = contaRepository.buscarPorIdComBloqueio(segundoId)
                                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

                var contaRemetente = idRemetente.equals(primeiroId)
                                ? primeiraConta
                                : segundaConta;

                var contaDestinatario = idDestinatario.equals(primeiroId)
                                ? primeiraConta
                                : segundaConta;

                BigDecimal valorTransferencia = BigDecimal.valueOf(valor);

                LocalDateTime agora = LocalDateTime.now();

                // A duplicidade deve ser verificada antes do saldo.
                boolean transferenciaDuplicada = transferenciaRepository
                                .existsByRemetenteAndDestinatarioAndValorAndDataHoraAfter(
                                                contaRemetente,
                                                contaDestinatario,
                                                valorTransferencia,
                                                agora.minusSeconds(60));

                if (transferenciaDuplicada) {
                        throw new IllegalArgumentException("TRANSFERENCIA_DUPLICADA");
                }

                if (contaRemetente.getSaldo().compareTo(valorTransferencia) < 0) {
                        throw new IllegalArgumentException("SALDO_INSUFICIENTE");
                }

                // Débito e crédito dentro da mesma transação.
                contaRemetente.setSaldo(
                                contaRemetente.getSaldo().subtract(valorTransferencia));

                contaDestinatario.setSaldo(
                                contaDestinatario.getSaldo().add(valorTransferencia));

                Transferencia transferencia = new Transferencia();
                transferencia.setRemetente(contaRemetente);
                transferencia.setDestinatario(contaDestinatario);
                transferencia.setValor(valorTransferencia);
                transferencia.setDataHora(agora);

                transferencia = transferenciaRepository.save(transferencia);

                return new TransferenciaResponse(
                                transferencia.getId(),
                                remetente.getUsername(),
                                destinatario.getUsername(),
                                valor,
                                contaRemetente.getSaldo().intValueExact());
        }
}
