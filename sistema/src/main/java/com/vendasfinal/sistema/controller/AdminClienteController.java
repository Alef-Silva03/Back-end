package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Cliente;
import com.vendasfinal.sistema.repository.ClienteRepository;
import com.vendasfinal.sistema.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/clientes")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "admin/clientes-lista"; 
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "admin/cliente-form";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes attr) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));
            model.addAttribute("cliente", cliente);
            return "admin/cliente-form";
        } catch (Exception e) {
            attr.addFlashAttribute("erro", e.getMessage());
            return "redirect:/admin/clientes";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente, 
                         @RequestParam(value = "imagem", required = false) MultipartFile imagem, 
                         RedirectAttributes attr) {
        try {
            if (cliente.getId() == null) {
                // NOVO CLIENTE
                cliente.setSenha(passwordEncoder.encode("123456")); // Senha inicial padrão
                cliente.setRole("ROLE_CLIENTE");
            } else {
                // EDIÇÃO DE CLIENTE EXISTENTE
                Cliente clienteDB = clienteRepository.findById(cliente.getId())
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
                
                // Impede que campos de segurança fiquem nulos se não estiverem no form
                cliente.setSenha(clienteDB.getSenha());
                cliente.setRole(clienteDB.getRole());
                
                // Preserva a foto antiga se nenhuma nova foi enviada
                if (imagem == null || imagem.isEmpty()) {
                    cliente.setFoto(clienteDB.getFoto());
                }
            }

            // Processamento da Imagem (Perfil)
            if (imagem != null && !imagem.isEmpty()) {
                String nomeFoto = fileStorageService.salvarArquivo(imagem, "perfil/");
                cliente.setFoto(nomeFoto);
            }

            clienteRepository.save(cliente);
            attr.addFlashAttribute("sucesso", "Cliente " + cliente.getNome() + " salvo com sucesso!");
            
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            // Se for novo, volta pro formulário de 'novo', se for edição, volta pro ID específico
            return cliente.getId() == null ? "redirect:/admin/clientes/novo" : "redirect:/admin/clientes/editar/" + cliente.getId();
        }
        return "redirect:/admin/clientes";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            clienteRepository.deleteById(id);
            attr.addFlashAttribute("sucesso", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            // Caso de erro por Integridade Referencial (FK em Pedidos)
            attr.addFlashAttribute("erro", "Não é possível excluir este cliente pois ele possui pedidos vinculados.");
        }
        return "redirect:/admin/clientes";
    }
}