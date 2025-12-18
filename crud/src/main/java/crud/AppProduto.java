package crud;

import java.sql.SQLException;
import java.util.Scanner;

public class AppProduto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProdutoDAO produtoDAO = new ProdutoDAO();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Bem-vindo ao sistema de gerenciamento de produtos!");

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Adicionar Produtos");
			System.out.println("2. Listar Produtos");
			System.out.println("3. Atualizar Produtos");
			System.out.println("4. Deletar Produto");
			System.out.println("5. Sair");

			int escolha = scanner.nextInt();
			scanner.nextLine();

			try {
				switch (escolha) {
				case 1:
					System.out.print("Nome: ");
					String nome = scanner.nextLine();

					System.out.print("preco: ");
					double preco = scanner.nextDouble();
					scanner.nextLine();

					System.out.print("qtd: ");
					int qtd = scanner.nextInt();
					scanner.nextLine();
					
					System.out.print("categoria: ");
					String categoria = scanner.nextLine();

					Produto produto = new Produto();
					produto.setNomeproduto(nome);
					produto.setPreco(preco);
					produto.setQtd(qtd);
					produto.setCategoria(categoria);

					produtoDAO.adicionarProduto(produto);
					System.out.println("Produto adicionado!");
					break;

				case 2:
					produtoDAO.listarProdutos()
							.forEach(u -> System.out.println(u.getIdproduto() + " - " + u.getNomeproduto() + " - "
									 + u.getPreco() + " - " + u.getQtd() + "-" + u.getCategoria()));
					break;

				case 3:
					System.out.print("Digite o ID do produto a ser atualizado: ");
					int idAtualizar = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Novo Nome: ");
					String novoproduto = scanner.nextLine();
					
					System.out.print("Novo preco: ");
					double novoPreco = scanner.nextDouble();
					scanner.nextLine();
					
					System.out.print("Nova qtd: ");
					int novaQtd = scanner.nextInt();
					scanner.nextLine();
					
					System.out.print("Nova Categoria: ");
					String novaCategoria = scanner.nextLine();


					Produto produtoAtualizar = new Produto();
					produtoAtualizar.setIdproduto(idAtualizar);
					produtoAtualizar.setNomeproduto(novoproduto);
					produtoAtualizar.setPreco(novoPreco);
					produtoAtualizar.setQtd(novaQtd);
					produtoAtualizar.setCategoria(novaCategoria);

					produtoDAO.atualizarProduto(produtoAtualizar);
					System.out.println("Produto atualizado com sucesso!");
					break;

				case 4:
					System.out.print("Digite o ID do produto a ser deletado: ");
					int idDeletar = scanner.nextInt();
					produtoDAO.deletarProduto(idDeletar);
					System.out.println("Produto deletado com sucesso! ");
					break;

				case 5:
					System.out.println("Saindo... ");
					scanner.close();
					return;

				default:
					System.out.println("Opção inválida! ");
				}
			} catch (SQLException e) {
				System.err.println("Erro: " + e.getMessage());

			}
		}

	}
}
