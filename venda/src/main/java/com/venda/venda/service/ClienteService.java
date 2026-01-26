package com.venda.venda.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.venda.venda.repository.ClienteRepository;
import com.venda.venda.model.Cliente;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public List<Cliente> listAll() {
        return repository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
