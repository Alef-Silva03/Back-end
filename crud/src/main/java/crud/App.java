package crud;

import java.sql.SQLException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Bem-vindo ao sistema de gerenciamento de usuários!");

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Adicionar Usuário");
			System.out.println("2. Listar Usuários");
			System.out.println("3. Atualizar Usuário");
			System.out.println("4. Deletar Usuário");
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

					System.out.print("Idade: ");
					int idade = scanner.nextInt();

					Usuario usuario = new Usuario();
					usuario.setNome(nome);
					usuario.setEmail(email);
					usuario.setIdade(idade);

					usuarioDAO.adicionarUsuario(usuario);
					System.out.println("Usuário adicionado!");
					break;

				case 2:
					usuarioDAO.listarUsuarios().forEach(u -> System.out
							.println(u.getId() + " - " + u.getNome() + " - " + u.getEmail() + " - " + u.getIdade()));
					break;

				case 3:
					System.out.print("Digite o ID do usuário a ser atualizado: ");
					int idAtualizar = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Novo Nome: ");
					String novoNome = scanner.nextLine();

					System.out.print("Novo Email: ");
					String novoEmail = scanner.nextLine();

					System.out.print("Nova Idade: ");
					int novaIdade = scanner.nextInt();

					Usuario usuarioAtualizar = new Usuario();
					usuarioAtualizar.setId(idAtualizar);
					usuarioAtualizar.setNome(novoNome);
					usuarioAtualizar.setEmail(novoEmail);
					usuarioAtualizar.setIdade(novaIdade);

					usuarioDAO.atualizarUsuario(usuarioAtualizar);
					System.out.println("Usuário atualizado com sucesso!");
					break;

				case 4:
					System.out.print("Digite o ID do usuário a ser deletado: ");
					int idDeletar = scanner.nextInt();
					usuarioDAO.deletarUsuario(idDeletar);
					System.out.println("Usuário deletado com sucesso!");
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
