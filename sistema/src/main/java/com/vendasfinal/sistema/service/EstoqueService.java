package com.vendasfinal.sistema.service;


import com.vendasfinal.sistema.model.Estoque;
import com.vendasfinal.sistema.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired private EstoqueRepository estoqueRepository;

    public void baixarEstoque(Long produtoId, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto sem registro de estoque"));
        
        if (estoque.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produtoId);
        }
        
        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        estoqueRepository.save(estoque);
    }

    public void adicionarEstoque(Long produtoId, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoqueRepository.save(estoque);
    }
}