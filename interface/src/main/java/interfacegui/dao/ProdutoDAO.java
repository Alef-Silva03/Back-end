package interfacegui.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfacegui.jdbc.ConnectionFactory;
import interfacegui.model.Produto;


public class ProdutoDAO {
    private Connection conn;

    public ProdutoDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }

    public void cadastrar(Produto obj) {
        try {
            String sql = "insert into produtos (nome, preco, estoque) values (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getEstoque());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    public List<Produto> listarProdutos() {
        try {
            List<Produto> lista = new ArrayList<>();
            String sql = "SELECT * FROM produtos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setEstoque(rs.getInt("estoque"));
                lista.add(p);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 // Método para Excluir
    public void excluir(int id) {
        try {
            String sql = "delete from produtos where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para Editar
    public void editar(Produto obj) {
        try {
            String sql = "update produtos set nome=?, preco=?, estoque=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getEstoque());
            stmt.setInt(4, obj.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void baixarEstoque(int id, int qtdVendida) {
        try {
            // SQL que subtrai a quantidade do estoque atual
            String sql = "update produtos set estoque = estoque - ? where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtdVendida);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao baixar estoque: " + e);
        }
    }
    public Produto buscarPorId(int id) {
        try {
            String sql = "select * from produtos where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Produto p = new Produto();
            if (rs.next()) {
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
            }
            return p;
        } catch (SQLException e) {
            return null;
        }
    }
}