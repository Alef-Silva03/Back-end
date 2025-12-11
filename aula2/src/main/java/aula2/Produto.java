package aula2;
// Produto com 2 atributos (nome, preco)
public class Produto {
	private String nome;
	private double preco;
	public Produto(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}
	public double getPreco() {
		return preco;
	}
	public String getNome() {
		return nome;
	}
}
