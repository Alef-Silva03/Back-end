package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Pedido;
import com.vendasfinal.sistema.model.Produto;
import com.vendasfinal.sistema.model.StatusPedido;
import com.vendasfinal.sistema.repository.PedidoRepository;
import com.vendasfinal.sistema.repository.ProdutoRepository;
import com.vendasfinal.sistema.service.DashBoardService;
import com.vendasfinal.sistema.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired private DashBoardService dashboardService;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private PedidoService pedidoService;

    // 1. DASHBOARD PRINCIPAL
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Métodos do DashboardService
        model.addAttribute("faturamentoMes", dashboardService.calcularTotalVendasConfirmadas());
        model.addAttribute("qtdPendentes", dashboardService.contarPedidosPorStatus(StatusPedido.PENDENTE));

        // Busca produtos com estoque baixo (lendo o campo sincronizado na tabela produtos)
        List<Produto> produtosEstoqueBaixo = produtoRepository.findByQuantidadeLessThan(5);
        model.addAttribute("qtdEstoqueBaixo", produtosEstoqueBaixo.size());
        model.addAttribute("produtosEstoqueBaixo", produtosEstoqueBaixo);
        
        // Exibe os 5 pedidos mais recentes
        model.addAttribute("pedidos", pedidoRepository.findTop5ByOrderByIdDesc());

        return "admin/dashboard";
    }

    // 2. LISTAGEM COMPLETA DE PEDIDOS
    @GetMapping("/pedidos")
    public String listarTodos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "admin/pedidos-lista"; 
    }

    // 3. DETALHES DO PEDIDO
    @GetMapping("/pedidos/detalhes/{id}")
    public String exibirDetalhes(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        
        model.addAttribute("pedido", pedido);
        return "admin/pedido-detalhes"; 
    }

    // 4. CONFIRMAR PAGAMENTO (A partir do Dashboard)
    @PostMapping("/pedidos/confirmar/{id}")
    public String confirmarPagamento(@PathVariable Long id, RedirectAttributes attr) {
        try {
            pedidoService.atualizarStatus(id, StatusPedido.CONFIRMADO);
            attr.addFlashAttribute("sucesso", "Pedido #" + id + " confirmado com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao confirmar: " + e.getMessage());
        }
        // Geralmente usado no dashboard, então redireciona para lá
        return "redirect:/admin/dashboard";
    }

    // 5. ATUALIZAR STATUS (Via formulário/select na lista de pedidos)
    @PostMapping("/pedidos/status")
    public String atualizarStatus(@RequestParam Long id, 
                                 @RequestParam StatusPedido novoStatus, 
                                 RedirectAttributes attr) {
        try {
            pedidoService.atualizarStatus(id, novoStatus);
            attr.addFlashAttribute("sucesso", "Status do pedido #" + id + " alterado para " + novoStatus);
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao atualizar status: " + e.getMessage());
        }
        // Redireciona para a lista geral de pedidos
        return "redirect:/admin/pedidos";
    }
}