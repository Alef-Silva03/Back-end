package com.oficina.gestao.repository;

import com.oficina.gestao.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    
    // O nome correto agora reflete o campo 'cliente' que está dentro de Veiculo
    List<OrdemServico> findByVeiculoClienteEmail(String email);

    List<OrdemServico> findByStatus(String status);
}