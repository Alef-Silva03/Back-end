package com.oficina.gestao.repository;

import com.oficina.gestao.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    // Busca todos os veículos de um cliente específico pelo ID do usuário
    List<Veiculo> findByClienteId(Long clienteId);
    
    // Busca por placa (útil para evitar duplicidade)
    Veiculo findByPlaca(String placa);
}