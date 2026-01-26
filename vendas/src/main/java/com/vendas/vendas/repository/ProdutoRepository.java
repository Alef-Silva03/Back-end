package com.vendas.vendas.repository;

import com.vendas.vendas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    // ✅ AGORA FUNCIONA: Campo 'estoque' existe
    List<Produto> findByEstoqueGreaterThan(Long estoque);
}
