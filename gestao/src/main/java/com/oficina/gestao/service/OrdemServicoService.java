package com.oficina.gestao.service;

import com.oficina.gestao.model.OrdemServico;
import com.oficina.gestao.model.Produto;
import com.oficina.gestao.model.StatusOS;
import com.oficina.gestao.repository.OrdemServicoRepository;
import com.oficina.gestao.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository osRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Salva a Ordem de Serviço. 
     * Antes de salvar, chama o cálculo do total baseado nos produtos.
     */
    public OrdemServico salvarOS(OrdemServico os) {
        os.calcularTotal(); 
        return osRepository.save(os);
    }

    /**
     * Finaliza a OS com segurança transacional.
     */
    @Transactional
    public OrdemServico finalizarOS(Long osId) {
        OrdemServico os = osRepository.findById(osId)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada"));

        // Proteção contra finalização dupla
        if (os.getStatus() == StatusOS.CONCLUIDA) {
            throw new RuntimeException("Esta OS já foi finalizada.");
        }

        // Processa o abate de estoque
        if (os.getProdutos() != null) {
            for (Produto p : os.getProdutos()) {
                Produto produtoEstoque = produtoRepository.findById(p.getId())
                        .orElseThrow(() -> new RuntimeException("Produto ID " + p.getId() + " não encontrado"));

                if (produtoEstoque.getQuantidadeEstoque() == null || produtoEstoque.getQuantidadeEstoque() < 1) {
                    throw new RuntimeException("Estoque insuficiente para: " + produtoEstoque.getNome());
                }

                // Deduz uma unidade
                produtoEstoque.setQuantidadeEstoque(produtoEstoque.getQuantidadeEstoque() - 1);
                produtoRepository.save(produtoEstoque);
            }
        }

        // Atualiza os dados finais da OS
        os.setStatus(StatusOS.CONCLUIDA);
        os.setDataFinalizacao(LocalDateTime.now());
        
        return osRepository.save(os);
    }

    public List<OrdemServico> listarTodas() {
        return osRepository.findAll();
    }
    
    public OrdemServico buscarPorId(Long id) {
        return osRepository.findById(id).orElse(null);
    }

    // Método para buscar OS de um cliente específico (usando o novo método do Repository)
    public List<OrdemServico> listarPorCliente(Long clienteId) {
        return osRepository.findByVeiculoClienteId(clienteId);
    }
}