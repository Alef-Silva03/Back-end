package crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {

	public void adicionarCliente(Cliente cliente) throws SQLException {
		String sql = "INSERT INTO cliente (nome, email, cpf) VALUES (?, ?, ?)";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.setString(3, cliente.getCpf());
			stmt.executeUpdate();
		}
	}

	// READ (listar todos)
	public List<Cliente> listarClientes() throws SQLException {
		List<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT * FROM cliente";

		try (Connection conn = ConexaoFactory.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdcliente(rs.getInt("idcliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCpf(rs.getString("cpf"));
				clientes.add(cliente);
			}
		}
		return clientes;
	}

	// UPDATE
	public void atualizarCliente(Cliente cliente) throws SQLException {
		String sql = "UPDATE cliente SET nome = ?, email = ?, cpf = ? WHERE idcliente = ?";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.setString(3, cliente.getCpf());
			stmt.setInt(4, cliente.getIdcliente());
			stmt.executeUpdate();
		}
	}

	// DELETE
	public void deletarCliente(int id) throws SQLException {
		String sql = "DELETE FROM cliente WHERE idcliente = ?";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

}
