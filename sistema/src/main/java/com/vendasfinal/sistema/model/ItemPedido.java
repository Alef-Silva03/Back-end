package com.vendasfinal.sistema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;
    private Double precoUnitario;
    private Double subtotal;

    // Construtor padrão exigido pelo Hibernate
    public ItemPedido() {
    }

    // Método para calcular o subtotal manualmente
    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        if (this.quantidade != null && this.precoUnitario != null) {
            this.subtotal = this.quantidade * this.precoUnitario;
        } else {
            this.subtotal = 0.0;
        }
    }

    // --- GETTERS E SETTERS MANUAIS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Double getSubtotal() {
        if (this.subtotal == null) {
            return (quantidade != null && precoUnitario != null) ? quantidade * precoUnitario : 0.0;
        }
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}