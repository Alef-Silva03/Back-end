package com.oficina.gestao.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    private String marca;
    private String modelo;
    private Integer ano;
    private String cor;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String fotoVeiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;
}