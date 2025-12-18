package crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
	
	public void adicionarProduto(Produto produto) throws SQLException {
		String sql = "INSERT INTO produto (nomeproduto, preco, qtd, categoria) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, produto.getNomeproduto());
			stmt.setDouble(2, produto.getPreco());
			stmt.setInt(3, produto.getQtd());
			stmt.setString(4, produto.getCategoria());
			stmt.executeUpdate();
		}

	}

	public List<Produto> listarProdutos() throws SQLException {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM produto";

		try (Connection conn = ConexaoFactory.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdproduto(rs.getInt("idproduto"));
				produto.setNomeproduto(rs.getString("nomeproduto"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setQtd(rs.getInt("qtd"));
				produto.setCategoria(rs.getString("categoria"));
				produtos.add(produto);
			}
		}
		return produtos;
	}
	public void atualizarProduto(Produto produto) throws SQLException {
		String sql = "UPDATE produto SET  nomeproduto = ?, preco = ?, qtd = ?, categoria = ? WHERE idproduto = ? ";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, produto.getNomeproduto());
			stmt.setDouble(2, produto.getPreco());
			stmt.setInt(3, produto.getQtd());
			stmt.setString(4, produto.getCategoria());
			stmt.setInt(5, produto.getIdproduto());
			stmt.executeUpdate();
		}
	}
	public void deletarProduto(int id) throws SQLException{
		String sql = "DELETE FROM produto WHERE idproduto = ?";
		try (Connection conn = ConexaoFactory.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}
	
}
