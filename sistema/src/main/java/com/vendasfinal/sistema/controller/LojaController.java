package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.*;
import com.vendasfinal.sistema.repository.*;
import com.vendasfinal.sistema.service.EstoqueService; // Importante
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LojaController {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private EstoqueService estoqueService; // Injeção do novo serviço

    @GetMapping("/")
    public String index() {
        return "redirect:/loja";
    }

    @GetMapping("/loja")
    public String vitrine(@RequestParam(required = false) String nome, 
                         @RequestParam(required = false) Double precoMax, 
                         Model model,
                         Authentication auth) {
        
        List<Produto> lista;
        
        if (nome != null && !nome.isEmpty()) {
            lista = produtoRepository.findByNomeContainingIgnoreCase(nome);
        } else if (precoMax != null) {
            lista = produtoRepository.findByPrecoLessThanEqual(precoMax);
        } else {
            lista = produtoRepository.findAll();
        }
        
        model.addAttribute("produtos", lista);
        model.addAttribute("logado", auth != null && auth.isAuthenticated());
        
        return "cliente/vitrine"; 
    }
    
    @PostMapping("/loja/comprar/{id}")
    public String comprarProduto(@PathVariable Long id, Authentication auth, RedirectAttributes attr) {
        if (auth == null || !auth.isAuthenticated()) {
            attr.addFlashAttribute("erro", "Você precisa estar logado para comprar.");
            return "redirect:/login";
        }

        try {
            Cliente cliente = clienteRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente não localizado."));

            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não localizado."));

            // --- LÓGICA DE ESTOQUE ATUALIZADA ---
            // O EstoqueService já valida se há saldo e faz o save na tabela 'estoque'
            estoqueService.baixarEstoque(id, 1);

            // Sincroniza o campo quantidade no objeto produto para manter o Dashboard Admin correto
            produto.setQuantidade(produto.getQuantidade() - 1);
            produtoRepository.save(produto);

            // Criando o Pedido
            Pedido novoPedido = new Pedido();
            novoPedido.setCliente(cliente);
            novoPedido.setDataPedido(LocalDateTime.now());
            novoPedido.setStatus(StatusPedido.PENDENTE);
            novoPedido.setItens(new ArrayList<>());

            // Criando o Item do Pedido
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(1);
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(novoPedido);
            item.setSubtotal(item.getPrecoUnitario() * item.getQuantidade());
            
            novoPedido.getItens().add(item);
            novoPedido.setValorTotal(item.getSubtotal()); 

            pedidoRepository.save(novoPedido);
            attr.addFlashAttribute("sucesso", "Pedido realizado com sucesso!");
            
        } catch (Exception e) {
            // O erro lançado pelo EstoqueService ("Estoque insuficiente") cairá aqui
            attr.addFlashAttribute("erro", e.getMessage());
            return "redirect:/loja";
        }
        return "redirect:/cliente/meus-pedidos";
    }

    @GetMapping("/cliente/meus-pedidos")
    public String meusPedidos(Model model, Authentication auth) {
        if (auth == null) return "redirect:/login";

        Cliente cliente = clienteRepository.findByEmail(auth.getName()).orElse(null);
        if (cliente != null) {
            List<Pedido> pedidos = pedidoRepository.findByCliente(cliente);
            model.addAttribute("pedidos", pedidos);
        }
        return "cliente/meus-pedidos";
    }

    @GetMapping("/cliente/pedido/{id}")
    public String exibirDetalhesDoPedido(@PathVariable Long id, Model model, Authentication auth) {
        return pedidoRepository.findById(id)
                .filter(p -> p.getCliente().getEmail().equals(auth.getName()))
                .map(p -> {
                    model.addAttribute("pedido", p);
                    return "cliente/pedido-detalhes";
                }).orElse("redirect:/cliente/meus-pedidos");
    }

    @PostMapping("/cliente/pedido/cancelar/{id}")
    public String cancelarPedido(@PathVariable Long id, Authentication auth, RedirectAttributes attr) {
        try {
            Pedido pedido = pedidoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pedido não localizado"));

            if (!pedido.getCliente().getEmail().equals(auth.getName())) {
                throw new RuntimeException("Acesso negado.");
            }

            if (pedido.getStatus() == StatusPedido.PENDENTE) {
                pedido.setStatus(StatusPedido.CANCELADO);
                
                // DEVOLUÇÃO AO ESTOQUE USANDO O SERVICE
                for (ItemPedido item : pedido.getItens()) {
                    // Devolve na tabela 'estoque'
                    estoqueService.adicionarEstoque(item.getProduto().getId(), item.getQuantidade());
                    
                    // Sincroniza o campo na tabela 'produtos'
                    Produto p = item.getProduto();
                    p.setQuantidade(p.getQuantidade() + item.getQuantidade());
                    produtoRepository.save(p);
                }
                
                pedidoRepository.save(pedido);
                attr.addFlashAttribute("sucesso", "Pedido cancelado e estoque atualizado.");
            } else {
                attr.addFlashAttribute("erro", "Apenas pedidos pendentes podem ser cancelados.");
            }
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao cancelar: " + e.getMessage());
        }
        return "redirect:/cliente/meus-pedidos";
    }
}