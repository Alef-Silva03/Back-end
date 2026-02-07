package com.oficina.gestao.repository;

import com.oficina.gestao.model.OrdemServico;
import com.oficina.gestao.model.StatusOS; // Importe o Enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    
    // 1. Corrigido para usar o Enum StatusOS em vez de String
    List<OrdemServico> findByStatus(StatusOS status);

    // 2. Corrigido: O Spring navega de Veiculo -> Cliente (Usuario) -> Id
    List<OrdemServico> findByVeiculoClienteId(Long id);

    // 3. REMOVIDO: Como você disse que "não tem mecânico", este método 
    // estava travando a aplicação por não encontrar o campo no Model.
}