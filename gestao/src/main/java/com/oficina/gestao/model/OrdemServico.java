package com.oficina.gestao.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricaoProblema;
    private String servicoExecutado;
    
    @Enumerated(EnumType.STRING)
    private StatusOS status;

    private Double valorTotal;

    private LocalDateTime dataAbertura;
    private LocalDateTime dataFinalizacao;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Usuario mecanico;

    @PrePersist
    protected void onCreate() {
        dataAbertura = LocalDateTime.now();
        if (status == null) status = StatusOS.ABERTA;
    }
}