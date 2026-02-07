package com.oficina.gestao.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    private String marca;
    private String modelo;
    private Integer ano;
    private String cor;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String fotoVeiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getFotoVeiculo() {
		return fotoVeiculo;
	}

	public void setFotoVeiculo(String fotoVeiculo) {
		this.fotoVeiculo = fotoVeiculo;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
    
}