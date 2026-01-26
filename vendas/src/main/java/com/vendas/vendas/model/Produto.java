package com.vendas.vendas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduto")
    private Long idproduto;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "estoque")  // ✅ CAMPO ESTOQUE ADICIONADO
    private Long estoque;

    // ✅ CONSTRUTORES
    public Produto() {}

    public Produto(String nome, BigDecimal preco, Long estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // ✅ GETTERS E SETTERS
    public Long getIdproduto() { return idproduto; }
    public void setIdproduto(Long idproduto) { this.idproduto = idproduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Long getEstoque() { return estoque; }  // ✅ GETTER ESTOQUE
    public void setEstoque(Long estoque) { this.estoque = estoque; }
}

