package com.vendasfinal.sistema.controller;

import com.vendasfinal.sistema.model.Produto;
import com.vendasfinal.sistema.repository.ProdutoRepository;
import com.vendasfinal.sistema.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // LISTAR TODOS OS PRODUTOS
    @GetMapping
    public String listar(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtos);
        return "admin/produtos-lista";
    }

    // EXIBIR FORMULÁRIO DE NOVO PRODUTO
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/produto-form";
    }

    // EXIBIR FORMULÁRIO DE EDIÇÃO
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes attr) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            model.addAttribute("produto", produto.get());
            return "admin/produto-form";
        } else {
            attr.addFlashAttribute("erro", "Produto não encontrado.");
            return "redirect:/admin/produtos";
        }
    }

    // SALVAR OU ATUALIZAR PRODUTO
    @PostMapping("/salvar")
    public String salvar(Produto produto, 
                         @RequestParam("imagem") MultipartFile imagem, 
                         RedirectAttributes attr) {
        try {
            // Lógica para Upload de Imagem
            if (imagem != null && !imagem.isEmpty()) {
                // Salva o novo arquivo e retorna o nome/caminho
                String nomeFoto = fileStorageService.salvarArquivo(imagem, "produtos/");
                produto.setFoto(nomeFoto);
            } else if (produto.getId() != null) {
                // Se estiver editando e não enviou foto nova, mantém a foto antiga do banco
                Produto produtoAntigo = produtoRepository.findById(produto.getId()).get();
                produto.setFoto(produtoAntigo.getFoto());
            }

            produtoRepository.save(produto);
            attr.addFlashAttribute("sucesso", "Produto salvo com sucesso!");
            
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao processar o produto: " + e.getMessage());
            return "redirect:/admin/produtos/novo";
        }
        
        return "redirect:/admin/produtos";
    }

    // EXCLUIR PRODUTO
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            // Verifica se o produto existe antes de deletar
            if (produtoRepository.existsById(id)) {
                produtoRepository.deleteById(id);
                attr.addFlashAttribute("sucesso", "Produto removido com sucesso!");
            } else {
                attr.addFlashAttribute("erro", "Produto não encontrado.");
            }
        } catch (Exception e) {
            // Caso o produto esteja vinculado a um pedido, o banco impedirá a exclusão (Integridade Referencial)
            attr.addFlashAttribute("erro", "Não é possível excluir este produto pois ele já faz parte de pedidos realizados.");
        }
        return "redirect:/admin/produtos";
    }
}