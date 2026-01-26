package com.vendas.vendas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpedido")
    private Long idpedido;

    @Column(name = "data")
    private Date data;

    @Column(name = "idproduto")
    private Long idproduto;

    @Column(name = "idusuario")  // ✅ CAMPO DIRETO para Repository
    private Long idusuario;

    @Column(name = "qtd")
    private Integer qtd;

    @Column(name = "total")
    private BigDecimal total;

    // ✅ CONSTRUTOR VAZIO (OBRIGATÓRIO JPA)
    public Pedido() {}

    // ✅ CONSTRUTOR COMPLETO
    public Pedido(Date data, Long idproduto, Long idusuario, Integer qtd, BigDecimal total) {
        this.data = data;
        this.idproduto = idproduto;
        this.idusuario = idusuario;
        this.qtd = qtd;
        this.total = total;
    }

    // ✅ GETTERS E SETTERS
    public Long getIdpedido() { return idpedido; }
    public void setIdpedido(Long idpedido) { this.idpedido = idpedido; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Long getIdproduto() { return idproduto; }
    public void setIdproduto(Long idproduto) { this.idproduto = idproduto; }

    public Long getIdusuario() { return idusuario; }  // ✅ CORRETO para findByIdusuario()
    public void setIdusuario(Long idusuario) { this.idusuario = idusuario; }

    public Integer getQtd() { return qtd; }
    public void setQtd(Integer qtd) { this.qtd = qtd; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}

