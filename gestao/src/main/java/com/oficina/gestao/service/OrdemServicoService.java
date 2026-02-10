package com.oficina.gestao.service;

import com.oficina.gestao.model.OrdemServico;
import com.oficina.gestao.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository repository;

    /**
     * Ajustado para bater com o nome do método no Repository:
     * De: findByVeiculoUsuarioEmail
     * Para: findByVeiculoClienteEmail
     */
    public List<OrdemServico> listarPorEmailCliente(String email) {
        return repository.findByVeiculoClienteEmail(email);
    }

    public List<OrdemServico> listarTodas() {
        return repository.findAll();
    }

    public OrdemServico buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public OrdemServico salvarOS(OrdemServico os) {
        // Importante: garante que os cálculos financeiros sejam feitos antes da persistência
        if (os != null) {
            os.calcularTotal();
        }
        return repository.save(os);
    }

    public OrdemServico finalizarOS(Long id) {
        OrdemServico os = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada com o ID: " + id));
        
        os.setStatus("CONCLUIDO");
        // Ao finalizar, é bom recalcular por garantia
        os.calcularTotal(); 
        return repository.save(os);
    }
}