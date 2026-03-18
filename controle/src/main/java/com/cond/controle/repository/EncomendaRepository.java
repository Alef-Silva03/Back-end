package com.cond.controle.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cond.controle.model.Encomenda;

@Repository
public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {

    /**
     * Busca todas as encomendas de um apartamento específico.
     * Útil para o morador filtrar apenas o que é dele no dashboard.
     */
    List<Encomenda> findByApartamento(String apartamento);

    /**
     * Busca apenas as encomendas que ainda não foram retiradas.
     * Útil para a portaria ter uma visão do que está ocupando espaço na prateleira.
     */
    List<Encomenda> findByRetiradaFalse();

    /**
     * Lista as encomendas por ordem de chegada (mais recentes primeiro).
     */
    List<Encomenda> findAllByOrderByDataRecebimentoDesc();
}