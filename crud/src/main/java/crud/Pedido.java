package crud;

import java.util.Date;

public class Pedido {
	private int idpedido;
	private int qtd;
	private Date data;
	private double total;
	private int idcliente;
	private int idproduto;

	// relacionamento produto com o cliente
	// campos extras para exibição (do JOIN no DAO)
	private String clienteNome;
	private String produtoNome;

	public Pedido() {
	}

	// construtor
	// construtor completo
	public Pedido(int idpedido, int qtd, Date data, double total, int idcliente, int idproduto) {
		this.idpedido = idpedido;
		this.qtd = qtd;
		this.data = data;
		this.total = total;
	}
	// getter e setter

	public int getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public int getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}

	// trazer as informações do relacionamento do cliente e do produto
	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	@Override
	public String toString() {
		return String.format(
				"Pedido [idpedido=%d, cliente=%s(idcliente=%d), " + "produto=%s(idproduto=%d), "
						+ "qtd=%d, data=%s, total=R$%.2f]",
				idpedido, clienteNome != null ? clienteNome : "N/D", idcliente,
				produtoNome != null ? produtoNome : "N/D", idproduto, qtd, data, total);
	}
}
