package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Pedido;
import com.vendasfinal.sistema.model.StatusPedido;
import com.vendasfinal.sistema.repository.PedidoRepository;
import com.vendasfinal.sistema.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    // Listar pedidos em uma página própria
    @GetMapping
    public String listarTodos(Model model) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        model.addAttribute("pedidos", pedidos);
        return "admin/pedidos-lista";
    }

    // Ver detalhes do pedido (Itens, Cliente, Endereço)
    @GetMapping("/detalhes/{id}")
    public String verDetalhes(@PathVariable Long id, Model model, RedirectAttributes attr) {
        return pedidoRepository.findById(id).map(pedido -> {
            model.addAttribute("pedido", pedido);
            return "admin/pedido-detalhes";
        }).orElseGet(() -> {
            attr.addFlashAttribute("erro", "Pedido não encontrado.");
            return "redirect:/admin/dashboard";
        });
    }

    // CONFIRMAR PAGAMENTO (Rota única agora)
    @PostMapping("/confirmar/{id}")
    public String confirmarPagamento(@PathVariable Long id, RedirectAttributes attr) {
        try {
            // Usando o Service para manter a consistência
            pedidoService.atualizarStatus(id, StatusPedido.CONFIRMADO);
            attr.addFlashAttribute("sucesso", "Pagamento do pedido #" + id + " confirmado!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao confirmar pedido: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    // Excluir pedido
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            pedidoRepository.deleteById(id);
            attr.addFlashAttribute("sucesso", "Pedido removido com sucesso.");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}