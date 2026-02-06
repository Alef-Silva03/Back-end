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
        if (auth == null) return "redirect:/login";

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
            // Busca o cliente persistido no banco para garantir que não alteramos dados indevidos
            Cliente clienteAtual = clienteRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Sessão inválida ou cliente não encontrado"));
            
            // Atualizando apenas campos permitidos (Segurança)
            clienteAtual.setNome(clienteDadosNovos.getNome());
            clienteAtual.setCep(clienteDadosNovos.getCep());
            clienteAtual.setLogradouro(clienteDadosNovos.getLogradouro());
            clienteAtual.setBairro(clienteDadosNovos.getBairro());
            clienteAtual.setCidade(clienteDadosNovos.getCidade());
            clienteAtual.setNumero(clienteDadosNovos.getNumero());
            clienteAtual.setComplemento(clienteDadosNovos.getComplemento());

            // Processamento da imagem de perfil
            if (imagem != null && !imagem.isEmpty()) {
                // O FileStorageService cuida da gravação física
                String nomeFoto = fileStorageService.salvarArquivo(imagem, "perfil/");
                clienteAtual.setFoto(nomeFoto);
            }

            clienteRepository.save(clienteAtual);
            attr.addFlashAttribute("sucesso", "Perfil atualizado com sucesso!");
            
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao atualizar perfil: " + e.getMessage());
        }
        return "redirect:/cliente/perfil";
    }
}