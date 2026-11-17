package com.projeto.transferencias.repository;

import com.projeto.transferencias.entity.Conta;
import com.projeto.transferencias.entity.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

        List<Transferencia> findByRemetenteOrDestinatarioOrderByDataHoraDesc(
                        Conta remetente, Conta destinatario);

        List<Transferencia> findAllByOrderByDataHoraDesc();

        boolean existsByRemetenteAndDestinatarioAndValorAndDataHoraAfter(
                        Conta remetente,
                        Conta destinatario,
                        java.math.BigDecimal valor,
                        LocalDateTime dataHora);
}
