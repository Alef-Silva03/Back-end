package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Pedido;
import com.vendasfinal.sistema.model.StatusPedido;
import com.vendasfinal.sistema.service.DashBoardService;
import com.vendasfinal.sistema.service.PedidoService;
import com.vendasfinal.sistema.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired private DashBoardService dashboardService;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PedidoService pedidoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Pedido> pedidos = pedidoRepository.findAll(); 
        LocalDateTime agora = LocalDateTime.now();

        // Cálculo de faturamento usando Double
        Double faturamentoMes = pedidos.stream()
                .filter(p -> p.getStatus() == StatusPedido.CONFIRMADO)
                .filter(p -> p.getDataPedido() != null && p.getDataPedido().getMonth() == agora.getMonth())
                .mapToDouble(p -> p.getValorTotal() != null ? p.getValorTotal() : 0.0)
                .sum();

        long pendentes = pedidos.stream()
                .filter(p -> p.getStatus() == StatusPedido.PENDENTE)
                .count();

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("faturamentoMes", faturamentoMes);
        model.addAttribute("qtdPendentes", pendentes);
        model.addAttribute("totalPedidos", pedidos.size());
        
        // Total geral do Service
        model.addAttribute("totalVendas", dashboardService.calcularTotalVendasConfirmadas());

        return "admin/dashboard";
    }

    // Método para atualizar status via Dropdown/Select
    @PostMapping("/pedidos/status")
    public String atualizarStatus(@RequestParam Long id, @RequestParam StatusPedido novoStatus) {
        pedidoService.atualizarStatus(id, novoStatus);
        return "redirect:/admin/dashboard";
    }
}