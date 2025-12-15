package aula2ex3;

public class Calculo {
	private double desconto;
	
	public void calcular(Funcionarios f) {
		desconto = f.getSalario() - f.getSalario() * 0.15;
	}
	public double getDesconto() {
		return desconto;
	}
	public static void main(String[] args) {
		Funcionarios f1 = new Funcionarios ("F1", 2500.00);
		Funcionarios f2 = new Funcionarios ("F2", 2500.00);
		Funcionarios f3 = new Funcionarios ("F3", 2500.00);
		Calculo calculo = new Calculo();
		calculo.calcular(f1);
		calculo.calcular(f2);
		calculo.calcular(f3);
		System.out.println("f1 = " + calculo.getDesconto());
		System.out.println("f2 = " + calculo.getDesconto());
		System.out.println("f3 = " + calculo.getDesconto());
	}
	
}
	


