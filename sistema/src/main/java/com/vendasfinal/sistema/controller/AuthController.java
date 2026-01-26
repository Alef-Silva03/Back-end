package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Cliente;
import com.vendasfinal.sistema.model.Usuario;
import com.vendasfinal.sistema.repository.UsuarioRepository;
import com.vendasfinal.sistema.service.FileStorageService; // Import necessário
import com.vendasfinal.sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    
    // CORREÇÃO: Injeção do serviço de armazenamento de arquivos
    @Autowired private FileStorageService fileStorageService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registrar")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastro";
    }

    @PostMapping("/registrar")
    public String realizarRegistro(Cliente cliente, 
                                   @RequestParam("tipoUsuario") String role, 
                                   @RequestParam(value = "imagem", required = false) MultipartFile imagem,
                                   RedirectAttributes attr) {
        try {
            // Processamento da imagem
            if (imagem != null && !imagem.isEmpty()) {
                String subPasta = role.equals("ADMIN") ? "admins/" : "clientes/";
                String nomeFoto = fileStorageService.salvarArquivo(imagem, subPasta);
                cliente.setFoto(nomeFoto);
            }
            
            usuarioService.registrar(cliente, role);
            attr.addFlashAttribute("sucesso", "Cadastro realizado com sucesso!");
            return "redirect:/login";
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro: " + e.getMessage());
            return "redirect:/registrar";
        }
    }

    @GetMapping("/home")
    public String redirecionarPorPerfil(Authentication auth) {
        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/cliente/meus-pedidos";
    }

    @PostMapping("/esqueci-senha")
    public String processarEsqueciSenha(@RequestParam("email") String email, RedirectAttributes attributes) {
        try {
            String token = usuarioService.gerarTokenRecuperacao(email);
            attributes.addFlashAttribute("sucesso", "Token de recuperação gerado!");
            return "redirect:/login";
        } catch (RuntimeException e) {
            attributes.addFlashAttribute("erro", "E-mail não localizado.");
            return "redirect:/login";
        }
    }

    @GetMapping("/resetar-senha")
    public String exibirFormularioReset(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "resetar-senha";
    }

    @PostMapping("/resetar-senha")
    public String atualizarSenha(@RequestParam("token") String token, 
                                 @RequestParam("senha") String novaSenha, 
                                 RedirectAttributes attributes) {
        Optional<Usuario> userOpt = usuarioRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();
            usuario.setSenha(passwordEncoder.encode(novaSenha));
            usuario.setResetToken(null);
            usuarioRepository.save(usuario);
            attributes.addFlashAttribute("sucesso", "Senha alterada!");
            return "redirect:/login";
        }
        attributes.addFlashAttribute("erro", "Token inválido!");
        return "redirect:/login";
    }
}