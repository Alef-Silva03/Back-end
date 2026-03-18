package com.cond.controle.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cond.controle.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Verifica se já existe uma reserva para o mesmo espaço na mesma data
    boolean existsByEspacoAndData(String espaco, LocalDate data);
    
    // Lista reservas de um usuário específico
    List<Reserva> findByUsuarioId(Long usuarioId);
}