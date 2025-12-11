package aula2ex3;

public class Funcionarios {
	private String nome;
	private double salario;
	public Funcionarios(String nome, double salario) {
		this.nome = nome;
		this.salario = salario;
	}
	public double getSalario() {
		return salario;
	}
	public String getNome() {
		return nome;
	}
	
}
