package com.oficina.gestao.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrdemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricaoProblema;
	private String servicoExecutado;

	@Enumerated(EnumType.STRING)
	private StatusOS status;

	private Double valorTotal;

	private LocalDateTime dataAbertura;
	private LocalDateTime dataFinalizacao;

	@ManyToOne
	@JoinColumn(name = "veiculo_id")
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "mecanico_id")
	private Usuario mecanico;

	@ManyToMany
	@JoinTable(name = "os_produtos", joinColumns = @JoinColumn(name = "ordem_servico_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
	private List<Produto> produtos;

	@PrePersist
	protected void onCreate() {
		this.dataAbertura = LocalDateTime.now();
		if (this.status == null)
			this.status = StatusOS.ORCAMENTO;
	}

	public void calcularTotal() {
		double totalProdutos = 0.0;
		if (this.produtos != null && !this.produtos.isEmpty()) {
			totalProdutos = this.produtos.stream().mapToDouble(p -> p.getPreco() != null ? p.getPreco() : 0.0).sum();
		}
		this.valorTotal = totalProdutos;
	}

	// --- GETTERS E SETTERS MANUAIS (Para eliminar erros de compilação) ---

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public StatusOS getStatus() {
		return status;
	}

	public void setStatus(StatusOS status) {
		this.status = status;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public LocalDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getServicoExecutado() {
		return servicoExecutado;
	}

	public void setServicoExecutado(String servicoExecutado) {
		this.servicoExecutado = servicoExecutado;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Usuario getMecanico() {
		return mecanico;
	}

	public void setMecanico(Usuario mecanico) {
		this.mecanico = mecanico;
	}
}