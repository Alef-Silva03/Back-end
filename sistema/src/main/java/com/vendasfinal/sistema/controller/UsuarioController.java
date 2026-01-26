package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Cliente;
import com.vendasfinal.sistema.model.Usuario;
import com.vendasfinal.sistema.repository.UsuarioRepository;
import com.vendasfinal.sistema.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/cadastro")
    public String exibirCadastro() {
        return "cadastro";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarNovoUsuario(HttpServletRequest request, 
                                   @RequestParam("tipoUsuario") String tipoUsuario,
                                   @RequestParam("imagem") MultipartFile imagem,
                                   RedirectAttributes attr) {
        try {
            Usuario usuario;

            // Lógica de Herança: Se for CLIENTE, criamos o objeto especializado
            if ("CLIENTE".equals(tipoUsuario)) {
                Cliente cliente = new Cliente();
                // Pegando dados específicos de cliente que vêm do formulário
                cliente.setCpf(request.getParameter("cpf"));
                cliente.setCep(request.getParameter("cep"));
                cliente.setLogradouro(request.getParameter("logradouro"));
                cliente.setBairro(request.getParameter("bairro"));
                cliente.setCidade(request.getParameter("cidade"));
                cliente.setNumero(request.getParameter("numero"));
                cliente.setComplemento(request.getParameter("complemento"));
                usuario = cliente;
            } else {
                usuario = new Usuario();
            }

            // Dados comuns a ambos (Admin e Cliente)
            usuario.setNome(request.getParameter("nome"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setSenha(passwordEncoder.encode(request.getParameter("senha")));
            usuario.setRole(tipoUsuario);

            // Upload de imagem
            if (imagem != null && !imagem.isEmpty()) {
                String nomeFoto = fileStorageService.salvarArquivo(imagem, "perfil/");
                usuario.setFoto(nomeFoto);
            }

            // O Hibernate detecta se 'usuario' é instância de Cliente e salva nas duas tabelas
            usuarioRepository.save(usuario);

            attr.addFlashAttribute("sucesso", "Cadastro realizado com sucesso!");
            return "redirect:/login";

        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            return "redirect:/cadastro";
        }
    }
}