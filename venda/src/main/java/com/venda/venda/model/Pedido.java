package com.venda.venda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity @Table(name="pedido")
public class Pedido {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="idcliente")
    private Cliente cliente;
    @ManyToOne @JoinColumn(name="idproduto")
    private Produto produto;
    @Column private int qtd;
    @Column(precision=10, scale=2, nullable = false) 
    private BigDecimal total;
    
    @Column private LocalDateTime datacriacao = LocalDateTime.now();
    // getters/setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public LocalDateTime getDatacriacao() {
		return datacriacao;
	}
	public void setDatacriacao(LocalDateTime datacriacao) {
		this.datacriacao = datacriacao;
	}
    
}
