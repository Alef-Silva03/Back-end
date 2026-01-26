package com.vendasfinal.sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime dataPedido = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.PENDENTE;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPedido> itens = new ArrayList<>();

	private Double valorTotal = 0.0;

	// Lógica de cálculo
	public void calcularTotal() {
		if (this.itens == null)
			return;
		this.valorTotal = itens.stream().mapToDouble(item -> item.getSubtotal() != null ? item.getSubtotal() : 0.0)
				.sum();
	}

	// --- GETTERS E SETTERS MANUAIS (Para funcionar sem Lombok) ---
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}