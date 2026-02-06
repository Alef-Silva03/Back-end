package com.vendasfinal.sistema.service;

import com.vendasfinal.sistema.model.Estoque;
import com.vendasfinal.sistema.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para integridade

@Service
public class EstoqueService {

    @Autowired 
    private EstoqueRepository estoqueRepository;

    /**
     * @Transactional garante que, se houver erro na compra, 
     * o estoque não seja baixado indevidamente.
     */
    @Transactional
    public void baixarEstoque(Long produtoId, Integer quantidadePedida) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto sem registro de estoque no sistema."));
        
        // Verifica se a quantidade em estoque é suficiente
        if (estoque.getQuantidade() < quantidadePedida) {
            throw new RuntimeException("Estoque insuficiente! Disponível: " + estoque.getQuantidade());
        }
        
        // Atualiza o saldo
        estoque.setQuantidade(estoque.getQuantidade() - quantidadePedida);
        
        // O save aqui persiste na tabela 'estoque'
        estoqueRepository.save(estoque);
    }

    @Transactional
    public void adicionarEstoque(Long produtoId, Integer quantidadeAdicional) {
        // Busca o estoque ou cria um novo se necessário (útil para reposição)
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Registro de estoque não localizado para o produto ID: " + produtoId));
        
        estoque.setQuantidade(estoque.getQuantidade() + quantidadeAdicional);
        estoqueRepository.save(estoque);
    }
}