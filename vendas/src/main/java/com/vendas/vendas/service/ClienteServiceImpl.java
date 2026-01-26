package com.vendas.vendas.service;

import com.vendas.vendas.model.Cliente;
import com.vendas.vendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service  // ← Remove 'public class ClienteService' → 'ClienteServiceImpl'
@Transactional(readOnly = true)
public class ClienteServiceImpl implements ClienteService {  // ← IMPLEMENTS!
    
    @Autowired
    private ClienteRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return repository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return repository.findById(id);
    }
    
    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        if (cliente == null || cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        return repository.save(cliente);
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        repository.deleteById(id);
    }
}
