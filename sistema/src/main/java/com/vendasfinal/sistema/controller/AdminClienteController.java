package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Cliente;
import com.vendasfinal.sistema.repository.ClienteRepository;
import com.vendasfinal.sistema.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/clientes")
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
    public String editarForm(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("cliente", cliente);
        return "admin/cliente-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente, 
                         @RequestParam(value = "imagem", required = false) MultipartFile imagem, 
                         RedirectAttributes attr) {
        try {
            if (cliente.getId() == null) {
                // Se for novo, define senha padrão e role
                cliente.setSenha(passwordEncoder.encode("123456"));
                cliente.setRole("CLIENTE");
            } else {
                // Se for edição, mantém a senha e role existentes
                Cliente clienteDB = clienteRepository.findById(cliente.getId()).get();
                cliente.setSenha(clienteDB.getSenha());
                cliente.setRole(clienteDB.getRole());
                if (imagem == null || imagem.isEmpty()) {
                    cliente.setFoto(clienteDB.getFoto());
                }
            }

            if (imagem != null && !imagem.isEmpty()) {
                String nomeFoto = fileStorageService.salvarArquivo(imagem, "perfil/");
                cliente.setFoto(nomeFoto);
            }

            clienteRepository.save(cliente);
            attr.addFlashAttribute("sucesso", "Cliente salvo com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao salvar cliente: " + e.getMessage());
        }
        return "redirect:/admin/clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            clienteRepository.deleteById(id);
            attr.addFlashAttribute("sucesso", "Cliente excluído!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Não é possível excluir um cliente com pedidos.");
        }
        return "redirect:/admin/clientes";
    }
}