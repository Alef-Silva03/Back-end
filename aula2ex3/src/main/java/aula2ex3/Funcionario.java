package aula2ex3;

	public class Funcionario {
	    private String nome;
	    private double salario;
	    
	    // Construtor
	    public Funcionario(String nome, double salario) {
	        this.nome = nome;
	        this.salario = salario;
	    }
	    
	    // Método de cálculo salário líquido (15% desconto)
	    public double calcularSalarioLiquido() {
	        return salario - (salario * 0.15);
	    }
	    
	    // Getter para exibição formatada
	    public String getDados() {
	        return String.format("Funcionário: %s | Bruto: R$%.2f | Líquido: R$%.2f", 
	                           nome, salario, calcularSalarioLiquido());
	    }
	}
