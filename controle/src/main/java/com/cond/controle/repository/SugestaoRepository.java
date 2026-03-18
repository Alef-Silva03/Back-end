package com.cond.controle.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cond.controle.model.Sugestao;
import java.util.List;

@Repository
public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {
    // Busca todas as sugestões ordenadas pela data mais recente
    List<Sugestao> findAllByOrderByDataDesc();
}