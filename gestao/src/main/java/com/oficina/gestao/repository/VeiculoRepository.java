package com.oficina.gestao.repository;



import com.oficina.gestao.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByClienteId(Long clienteId);
    Optional<Veiculo> findByPlaca(String placa);
}