package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.*;
import com.vendasfinal.sistema.repository.*;
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

    // EXIBIR VITRINE DE PRODUTOS
    @GetMapping("/loja")
    public String vitrine(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "cliente/vitrine";
    }

    // PROCESSAR COMPRA DE UM PRODUTO
    @PostMapping("/loja/comprar/{id}")
    public String comprarProduto(@PathVariable Long id, Authentication auth, RedirectAttributes attr) {
        try {
            Cliente cliente = clienteRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente não localizado"));

            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não localizado"));

            Pedido novoPedido = new Pedido();
            novoPedido.setCliente(cliente);
            novoPedido.setDataPedido(LocalDateTime.now());
            novoPedido.setStatus(StatusPedido.PENDENTE);
            
            // Inicialização manual da lista (importante sem Lombok)
            novoPedido.setItens(new ArrayList<>());

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(1);
            
            // Conversão de BigDecimal para Double
            if (produto.getPreco() != null) {
                item.setPrecoUnitario(produto.getPreco().doubleValue());
            }
            
            item.setPedido(novoPedido);
            
            novoPedido.getItens().add(item);
            item.calcularSubtotal();    
            novoPedido.calcularTotal(); 

            pedidoRepository.save(novoPedido);

            attr.addFlashAttribute("sucesso", "Pedido #" + novoPedido.getId() + " realizado com sucesso!");
            
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao processar compra: " + e.getMessage());
        }
        return "redirect:/loja";
    }

    // EXIBIR HISTÓRICO DE PEDIDOS (Único mapeamento para /cliente/meus-pedidos)
    @GetMapping("/cliente/meus-pedidos")
    public String meusPedidos(Model model, Authentication auth) {
        try {
            Cliente cliente = clienteRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente não localizado"));
            
            List<Pedido> pedidosDoCliente = pedidoRepository.findByCliente(cliente);
            model.addAttribute("pedidos", pedidosDoCliente);
        } catch (Exception e) {
            model.addAttribute("erro", "Não foi possível carregar seus pedidos.");
        }
        return "cliente/meus-pedidos";
    }
}