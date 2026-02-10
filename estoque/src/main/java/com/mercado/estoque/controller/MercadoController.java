package com.mercado.estoque.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.mercado.estoque.model.*;
import com.mercado.estoque.repository.*;
import com.mercado.estoque.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MercadoController {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private VendaRepository vendaRepository;
    @Autowired private UsuarioService usuarioService;

    @PostMapping("/public/usuarios/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.salvar(usuario));
    }

    @GetMapping("/public/produtos/{codigo}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable String codigo) {
        // Busca apenas produtos ativos para o Terminal
        return produtoRepository.findByCodigoBarras(codigo)
                .filter(Produto::isAtivo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/public/venda/finalizar")
    @Transactional
    public ResponseEntity<Venda> finalizarVenda(@RequestBody Pedido pedidoDeEntrada) {
        // 1. Criar e persistir o Pedido primeiro
        Pedido pedido = new Pedido();
        pedido.setItens(new ArrayList<>()); 
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        double total = 0;
        for (ItemPedido item : pedidoDeEntrada.getItens()) {
            Produto p = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            // Validar estoque
            if (p.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para: " + p.getNome());
            }
            
            // Atualizar estoque
            p.setQuantidadeEstoque(p.getQuantidadeEstoque() - item.getQuantidade());
            produtoRepository.save(p);

            // Configurar item (Preço unitário vem do banco por segurança, não do front)
            item.setPedido(pedidoSalvo);
            item.setPrecoUnitario(p.getPreco());
            total += (p.getPreco() * item.getQuantidade());
            pedidoSalvo.getItens().add(item);
        }

        // 2. Salvar a Venda vinculada ao Pedido
        Venda venda = new Venda();
        venda.setPedido(pedidoSalvo);
        venda.setValorTotal(total);
       
        venda.setDataVenda(java.time.LocalDateTime.now());
        return ResponseEntity.ok(vendaRepository.save(venda));
    }

    // --- PAINEL ADMINISTRATIVO ---

    @GetMapping("/admin/estoque")
    public List<Produto> listarEstoque() {
        // Retorna todos para o Admin (ativos e inativos) ordenados por nome
        return produtoRepository.findAll().stream()
                .sorted(Comparator.comparing(Produto::getNome))
                .collect(Collectors.toList());
    }

    @GetMapping("/admin/resumo")
    public Map<String, Object> buscarResumo() {
        Map<String, Object> resumo = new HashMap<>();
        List<Venda> vendas = vendaRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();
        
        double totalFaturado = vendas.stream().mapToDouble(Venda::getValorTotal).sum();
        
        long itensFaltando = produtos.stream()
                .filter(p -> p.isAtivo() && (p.getQuantidadeEstoque() == null || p.getQuantidadeEstoque() < 5))
                .count();

        resumo.put("faturamentoTotal", totalFaturado);
        resumo.put("quantidadeVendas", (long) vendas.size());
        resumo.put("produtosEmAlerta", itensFaltando);
        return resumo;
    }

    @GetMapping("/admin/vendas/recentes")
    public List<Venda> listarVendasRecentes() {
        return vendaRepository.findAll().stream()
                .sorted(Comparator.comparing(Venda::getId).reversed())
                .limit(10) // Aumentado para 10 para melhor visualização
                .collect(Collectors.toList());
    }

    @PostMapping("/admin/produtos")
    public ResponseEntity<Produto> salvarProduto(@RequestBody Produto produto) {
        // Se for um novo produto e não vier imagem, define a padrão
        if (produto.getUrlImagem() == null || produto.getUrlImagem().isEmpty()) {
            produto.setUrlImagem("/produtos/default.jpg");
        }
        
        // Garante que o status ativo seja preservado ou definido como true se for novo
        if (produto.getId() == null) {
            produto.setAtivo(true);
        }

        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    @DeleteMapping("/admin/produtos/{id}")
    @Transactional
    public ResponseEntity<Void> alternarStatus(@PathVariable Long id) {
        return produtoRepository.findById(id).map(p -> {
            p.setAtivo(!p.isAtivo());
            produtoRepository.save(p);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}