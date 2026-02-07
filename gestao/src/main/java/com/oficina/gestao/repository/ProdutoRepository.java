package com.oficina.gestao.repository;


import com.oficina.gestao.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Busca peças que estão acabando (ex: menos de 5 unidades)
    List<Produto> findByQuantidadeEstoqueLessThan(Integer quantidade);
}