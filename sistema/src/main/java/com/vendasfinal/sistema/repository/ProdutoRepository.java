package com.vendasfinal.sistema.repository;

import com.vendasfinal.sistema.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    // 1. Busca produtos que contenham parte do nome (ignore maiúsculas/minúsculas)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // 2. Busca produtos com preço menor ou igual ao informado
    List<Produto> findByPrecoLessThanEqual(Double precoMax);

    // 3. Método que usamos no Dashboard para estoque baixo
    List<Produto> findByQuantidadeLessThan(Integer limite);
}