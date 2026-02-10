package com.oficina.gestao.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataAbertura = LocalDateTime.now();
    
    @Column(nullable = false)
    private String status = "ORÇAMENTO"; // Valor padrão para evitar NullPointer

    @ManyToOne(fetch = FetchType.EAGER) // Garante que os dados do veículo venham junto no login do cliente
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Usuario mecanicoResponsavel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "os_servicos",
        joinColumns = @JoinColumn(name = "os_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicosRealizados = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "os_produtos",
        joinColumns = @JoinColumn(name = "os_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtosUtilizados = new ArrayList<>();

    private Double valorTotal = 0.0;

    // Métodos Getter e Setter (Lombok @Data já os cria, mas mantê-los manualmente não causa erro)

    /**
     * Calcula o total de forma segura. 
     * Chamamos este método no Service antes de salvar no banco.
     */
    public void calcularTotal() {
        double totalServicos = (servicosRealizados == null) ? 0.0 :
            servicosRealizados.stream()
                .filter(s -> s != null && s.getPrecoMaoDeObra() != null)
                .mapToDouble(Servico::getPrecoMaoDeObra)
                .sum();

        double totalProdutos = (produtosUtilizados == null) ? 0.0 :
            produtosUtilizados.stream()
                .filter(p -> p != null && p.getPrecoVenda() != null)
                .mapToDouble(Produto::getPrecoVenda)
                .sum();

        this.valorTotal = totalServicos + totalProdutos;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Usuario getMecanicoResponsavel() {
		return mecanicoResponsavel;
	}

	public void setMecanicoResponsavel(Usuario mecanicoResponsavel) {
		this.mecanicoResponsavel = mecanicoResponsavel;
	}

	public List<Servico> getServicosRealizados() {
		return servicosRealizados;
	}

	public void setServicosRealizados(List<Servico> servicosRealizados) {
		this.servicosRealizados = servicosRealizados;
	}

	public List<Produto> getProdutosUtilizados() {
		return produtosUtilizados;
	}

	public void setProdutosUtilizados(List<Produto> produtosUtilizados) {
		this.produtosUtilizados = produtosUtilizados;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

    // Getters e Setters omitidos aqui para brevidade (continue usando os seus)...
    
}