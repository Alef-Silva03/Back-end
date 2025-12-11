package teste;

public class Aluno {
	private String nome;
	private int idade;
	public Aluno(String nome, int idade) {
		// TODO Auto-generated constructor stub
		this.nome = nome;
		this.idade = idade;
	}
	public String toString() { return nome + "(" + idade + " anos)";
	}
}
//getter e setter
	// Em Java, get(getter) e set(setter) são métodos usados para acessar e modificar atributos de uma classe de forma controlada,
	// sendo uma parte essencial do encapsulamento.