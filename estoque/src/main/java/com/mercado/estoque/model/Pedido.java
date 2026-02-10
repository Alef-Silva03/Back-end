package com.mercado.estoque.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime dataCriacao = LocalDateTime.now();

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	// O JsonManagedReference trabalha junto com o JsonBackReference no ItemPedido
	// para evitar que o Jackson entre em loop infinito
	@JsonManagedReference
	private List<ItemPedido> itens = new ArrayList<>();

	// Construtor Padrão
	public Pedido() {
	}

	// Getters e Setters Manuais (Seguro se o Lombok falhar)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public Double getTotalPedido() {
		if (itens == null)
			return 0.0;
		return itens.stream().mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade()).sum();
	}
}