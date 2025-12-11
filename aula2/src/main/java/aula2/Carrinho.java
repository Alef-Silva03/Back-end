package aula2;

public class Carrinho {
	private double total;
	public void adicionar(Produto p) {
		total += p.getPreco();
	}
	public double getTotal() {
		return total;
	}
	public static void main(String[] args) {
		Produto p1 = new Produto("Notebook", 3500.00);
		Produto p2 = new Produto("Table", 2000.00);
		Produto p3 = new Produto("Smartphone", 6500.00);
		Produto p4 = new Produto("Desktop", 2300.00);
		Carrinho carrinho = new Carrinho();
		carrinho.adicionar(p1);
		carrinho.adicionar(p2);
		carrinho.adicionar(p3);
		carrinho.adicionar(p4);
		System.out.println("Total: R$" + carrinho.getTotal());
	}

}
