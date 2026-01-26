package com.venda.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.venda.venda.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
