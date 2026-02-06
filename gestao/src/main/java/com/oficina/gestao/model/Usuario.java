package com.oficina.gestao.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    private String telefone;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String foto;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnore // Evita erro de recursão ao listar usuários
    private List<Veiculo> veiculos;
}