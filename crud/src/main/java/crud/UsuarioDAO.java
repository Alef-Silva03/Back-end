package crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
	// inserir inser into sql ações Crud sql
	// alteração update
	// apagar delete
	// consultar o select
	// CREATE
	public void adicionarUsuario(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO usuarios (nome, email, idade) VALUES (?, ?, ?)";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setInt(3, usuario.getIdade());
			stmt.executeUpdate();
		}
	}

	// READ (listar todos)
	public List<Usuario> listarUsuarios() throws SQLException {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM usuarios";

		try (Connection conn = ConexaoFactory.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setIdade(rs.getInt("idade"));
				usuarios.add(usuario);
			}
		}
		return usuarios;
	}

	// UPDATE
	public void atualizarUsuario(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuarios SET nome = ?, email = ?, idade = ? WHERE id = ?";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setInt(3, usuario.getIdade());
			stmt.setInt(4, usuario.getId());
			stmt.executeUpdate();
		}
	}

	// DELETE
	public void deletarUsuario(int id) throws SQLException {
		String sql = "DELETE FROM usuarios WHERE id = ?";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}
}