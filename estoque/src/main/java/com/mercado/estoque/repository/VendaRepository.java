package com.mercado.estoque.repository;

import com.mercado.estoque.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}