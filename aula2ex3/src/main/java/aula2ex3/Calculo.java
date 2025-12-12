package aula2ex3;

public class Calculo {
	private double salario;
	public void calcular(Funcionarios f) {
		salario += s.getSalario();
	}
	public double getSalario() {
		return salario;
	}
	public static void main(String[] args) {
		Funcionarios f1 = new Funcionarios ("F1", 2500.00);
		Funcionarios f2 = new Funcionarios ("F2", 2500.00);
		Funcionarios f3 = new Funcionarios ("F3", 2500.00);
		Calculo calculo = new Calculo();
		calculo.adicionar(f1);
		calculo.adicionar(f2);
		calculo.adicionar(f3);
		
	}

}
