package com.projeto.transferencias.repository;

import java.math.BigDecimal;
import com.projeto.transferencias.entity.Conta;
import com.projeto.transferencias.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByUsuario(Usuario usuario);

    Optional<Conta> findByUsuarioId(Long usuarioId);

    @Query("SELECT c.id FROM Conta c WHERE c.usuario.id = :usuarioId")
    Optional<Long> buscarIdPorUsuarioId(@Param("usuarioId") Long usuarioId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Conta c WHERE c.id = :id")
    Optional<Conta> buscarPorIdComBloqueio(@Param("id") Long id);

    @Query("SELECT COALESCE(SUM(c.saldo), 0) FROM Conta c")
    BigDecimal somarSaldos();

}
