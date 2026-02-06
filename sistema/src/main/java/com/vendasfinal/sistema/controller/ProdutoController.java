package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Estoque;
import com.vendasfinal.sistema.model.Produto;
import com.vendasfinal.sistema.repository.EstoqueRepository;
import com.vendasfinal.sistema.repository.ProdutoRepository;
import com.vendasfinal.sistema.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/produtos")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository; // Injetado para resolver o problema do estoque

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public String listar(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtos);
        return "admin/produtos-lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/produto-form";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes attr) {
        return produtoRepository.findById(id).map(produto -> {
            model.addAttribute("produto", produto);
            return "admin/produto-form";
        }).orElseGet(() -> {
            attr.addFlashAttribute("erro", "Produto não encontrado.");
            return "redirect:/admin/produtos";
        });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, 
                         @RequestParam(value = "imagem", required = false) MultipartFile imagem, 
                         RedirectAttributes attr) {
        try {
            // 1. Lógica de Imagem
            if (produto.getId() != null) {
                Produto produtoBanco = produtoRepository.findById(produto.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
                
                if (imagem == null || imagem.isEmpty()) {
                    produto.setFoto(produtoBanco.getFoto());
                } else {
                    String nomeFoto = fileStorageService.salvarArquivo(imagem, "produtos/");
                    produto.setFoto(nomeFoto);
                }
            } else {
                if (imagem != null && !imagem.isEmpty()) {
                    String nomeFoto = fileStorageService.salvarArquivo(imagem, "produtos/");
                    produto.setFoto(nomeFoto);
                }
            }

            // 2. Salva o Produto na tabela 'produtos'
            Produto produtoSalvo = produtoRepository.save(produto);

            // 3. SINCRONIZAÇÃO COM A TABELA 'ESTOQUE'
            // Isso garante que nunca falte o registro que o EstoqueService procura
            Estoque estoque = estoqueRepository.findByProdutoId(produtoSalvo.getId())
                    .orElse(new Estoque()); // Se não existir, cria um novo objeto
            
            estoque.setProduto(produtoSalvo);
            estoque.setQuantidade(produtoSalvo.getQuantidade()); // Pega a quantidade digitada no form
            
            estoqueRepository.save(estoque); // Salva na tabela 'estoque'

            attr.addFlashAttribute("sucesso", "Produto e estoque salvos com sucesso!");
            
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao processar: " + e.getMessage());
            return "redirect:/admin/produtos/novo";
        }
        
        return "redirect:/admin/produtos";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            // O JPA cuidará da exclusão do estoque se o cascade estiver correto, 
            // mas por segurança podemos verificar a existência.
            if (produtoRepository.existsById(id)) {
                produtoRepository.deleteById(id);
                attr.addFlashAttribute("sucesso", "Produto removido com sucesso!");
            } else {
                attr.addFlashAttribute("erro", "Produto não encontrado.");
            }
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Não é possível excluir: este produto possui pedidos vinculados.");
        }
        return "redirect:/admin/produtos";
    }
}