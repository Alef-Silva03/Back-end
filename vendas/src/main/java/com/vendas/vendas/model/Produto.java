package com.vendas.vendas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idproduto")
	private long idproduto;

	@Column(name = "nomeproduto", length = 100, nullable = false)
	private String nomeproduto;

	@Column(name = "categoria", length = 50)
	private String categoria;

	@Column(name = "preco", nullable = false)
	private Float preco;

	@Column(name = "estoque")
	private Integer estoque = 0;

	// Construtores
	public Produto() {
	}

	public Produto(String nomeproduto, String categoria, Float preco, Integer estoque) {
		this.nomeproduto = nomeproduto;
		this.categoria = categoria;
		this.preco = preco;
		this.estoque = estoque;
	}

	// Getters e Setters (encapsulamento OO)

	public long getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(long idproduto) {
		this.idproduto = idproduto;
	}

	public String getNomeproduto() {
		return nomeproduto;
	}

	public void setNomeproduto(String nomeproduto) {
		this.nomeproduto = nomeproduto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

}
