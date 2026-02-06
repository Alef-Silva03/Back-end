package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Cliente;
import com.vendasfinal.sistema.model.Usuario;
import com.vendasfinal.sistema.dto.UsuarioDTO;
import com.vendasfinal.sistema.repository.UsuarioRepository;
import com.vendasfinal.sistema.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private FileStorageService fileStorageService;

    @GetMapping("/cadastro")
    public String exibirCadastro(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "cadastro";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarNovoUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO dto, 
                                   @RequestParam(value = "imagem", required = false) MultipartFile imagem,
                                   RedirectAttributes attr) {
        try {
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                attr.addFlashAttribute("erro", "Este e-mail já está cadastrado.");
                return "redirect:/cadastro";
            }

            Usuario usuario;
            if ("CLIENTE".equals(dto.getTipoUsuario())) {
                Cliente cliente = new Cliente();
                cliente.setCpf(dto.getCpf());
                cliente.setCep(dto.getCep());
                cliente.setLogradouro(dto.getLogradouro());
                cliente.setBairro(dto.getBairro());
                cliente.setCidade(dto.getCidade());
                cliente.setNumero(dto.getNumero());
                cliente.setComplemento(dto.getComplemento());
                usuario = cliente;
            } else {
                usuario = new Usuario();
            }

            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            usuario.setRole(dto.getTipoUsuario());

            if (imagem != null && !imagem.isEmpty()) {
                String nomeFoto = fileStorageService.salvarArquivo(imagem, "perfil/");
                usuario.setFoto(nomeFoto);
            }

            usuarioRepository.save(usuario);
            attr.addFlashAttribute("sucesso", "Cadastro realizado! Faça seu login.");
            return "redirect:/login";

        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            return "redirect:/cadastro";
        }
    }
}