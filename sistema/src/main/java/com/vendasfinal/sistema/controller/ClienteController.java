package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Cliente;
import com.vendasfinal.sistema.repository.ClienteRepository;
import com.vendasfinal.sistema.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired 
    private ClienteRepository clienteRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/perfil")
    public String exibirPerfil(Authentication auth, Model model) {
        Cliente cliente = clienteRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        model.addAttribute("cliente", cliente);
        return "cliente/perfil"; 
    }

    @PostMapping("/perfil/atualizar")
    public String atualizarPerfil(@ModelAttribute Cliente clienteDadosNovos, 
                                   @RequestParam(value = "imagem", required = false) MultipartFile imagem,
                                   Authentication auth, 
                                   RedirectAttributes attr) {
        try {
            Cliente clienteAtual = clienteRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            
            // Atualizando dados
            clienteAtual.setNome(clienteDadosNovos.getNome());
            clienteAtual.setCep(clienteDadosNovos.getCep());
            clienteAtual.setLogradouro(clienteDadosNovos.getLogradouro());
            clienteAtual.setBairro(clienteDadosNovos.getBairro());
            clienteAtual.setCidade(clienteDadosNovos.getCidade());
            clienteAtual.setNumero(clienteDadosNovos.getNumero());
            clienteAtual.setComplemento(clienteDadosNovos.getComplemento());

            if (imagem != null && !imagem.isEmpty()) {
                String nomeFoto = fileStorageService.salvarArquivo(imagem, "perfil/");
                clienteAtual.setFoto(nomeFoto);
            }

            clienteRepository.save(clienteAtual);
            attr.addFlashAttribute("sucesso", "Perfil atualizado!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }
        return "redirect:/cliente/perfil";
    }
}