package com.venda.venda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.venda.venda.repository.ProdutoRepository;
import com.venda.venda.model.Produto;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public List<Produto> listAll() {
        return repository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return repository.findById(id);
    }

    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
