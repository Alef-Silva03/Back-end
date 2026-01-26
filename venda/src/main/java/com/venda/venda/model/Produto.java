package com.venda.venda.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nomeproduto", nullable = false, length = 100)
    private String nomeProduto;
    
    @Column(length = 50)
    private String categoria;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;  // ← MUDANÇA: Float → BigDecimal
    
    @Column
    private Integer estoque;

    // Construtores
    public Produto() {}
    
    public Produto(String nomeProduto, String categoria, BigDecimal preco, Integer estoque) {
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nomeProduto='" + nomeProduto + "', preco=" + preco + ", estoque=" + estoque + '}';
    }
}
