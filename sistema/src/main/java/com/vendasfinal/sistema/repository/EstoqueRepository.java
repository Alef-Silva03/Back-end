package com.vendasfinal.sistema.repository;

import com.vendasfinal.sistema.model.Estoque;
import com.vendasfinal.sistema.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
 // Busca o estoque passando o objeto produto (Relacionamento OO)
 Optional<Estoque> findByProduto(Produto produto);
 
 // Ou buscando pelo ID do produto dentro do objeto estoque
 Optional<Estoque> findByProdutoId(Long produtoId);
}