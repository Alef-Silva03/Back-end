package crud;

import java.sql.SQLException;
import java.util.Scanner;

public class AppCliente {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClienteDAO clienteDAO = new ClienteDAO();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Bem-vindo ao sistema de gerenciamento de Clientes!");

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Adicionar Clientes");
			System.out.println("2. Listar Clientes");
			System.out.println("3. Atualizar Clientes");
			System.out.println("4. Deletar Cliente");
			System.out.println("5. Sair");

			int escolha = scanner.nextInt();
			scanner.nextLine(); // consumir nova linha

			try {
				switch (escolha) {
				case 1:
					System.out.print("Nome: ");
					String nome = scanner.nextLine();

					System.out.print("Email: ");
					String email = scanner.nextLine();

					System.out.print("Cpf: ");
					String cpf = scanner.nextLine();

					Cliente cliente = new Cliente();
					cliente.setNome(nome);
					cliente.setEmail(email);
					cliente.setCpf(cpf);

					clienteDAO.adicionarCliente(cliente);
					System.out.println("Cliente adicionado!");
					break;

				case 2:
					clienteDAO.listarClientes().forEach(u -> System.out
							.println(u.getIdcliente() + " - " + u.getNome() + " - " + u.getEmail() + " - " + u.getCpf()));
					break;

				case 3:
					System.out.print("Digite o ID do usuário a ser atualizado: ");
					int idAtualizar = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Novo Nome: ");
					String novoNome = scanner.nextLine();

					System.out.print("Novo Email: ");
					String novoEmail = scanner.nextLine();

					System.out.print("Nova Cpf: ");
					String novoCpf = scanner.nextLine();

					Cliente clienteAtualizar = new Cliente();
					clienteAtualizar.setIdcliente(idAtualizar);
					clienteAtualizar.setNome(novoNome);
					clienteAtualizar.setEmail(novoEmail);
					clienteAtualizar.setCpf(novoCpf);

					clienteDAO.atualizarCliente(clienteAtualizar);
					System.out.println("Cliente atualizado com sucesso!");
					break;

				case 4:
					System.out.print("Digite o ID do usuário a ser deletado: ");
					int idDeletar = scanner.nextInt();
					clienteDAO.deletarCliente(idDeletar);
					System.out.println("Cliente deletado com sucesso!");
					break;

				case 5:
					System.out.println("Saindo...");
					scanner.close();
					return;

				default:
					System.out.println("Opção inválida!");
				}
			} catch (SQLException e) {
				System.err.println("Erro: " + e.getMessage());
			}
		}
	}
}
