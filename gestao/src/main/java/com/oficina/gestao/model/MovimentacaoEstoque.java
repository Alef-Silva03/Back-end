package com.oficina.gestao.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class MovimentacaoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Produto produto;

    private Integer quantidade;
    private String tipo; // ENTRADA ou SAIDA
    private LocalDateTime dataMovimentacao = LocalDateTime.now();
}