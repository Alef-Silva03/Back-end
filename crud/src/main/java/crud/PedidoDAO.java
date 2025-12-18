package crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
	public void adicionar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (qtd, data, total, idcliente, idproduto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
          
            stmt.setInt(1, pedido.getQtd());
            stmt.setDate(2, new java.sql.Date(pedido.getData().getTime()));  // REMOVIDO CAST DESNECESSÁRIO
            stmt.setDouble(3, pedido.getTotal());
            stmt.setInt(4, pedido.getIdcliente());
            stmt.setInt(5, pedido.getIdproduto());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setIdpedido(rs.getInt(1));
                    }
                }
            }
        }
    }
    
    public List<Pedido> listar() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = """
            SELECT p.*, c.nome as cliente_nome, pr.nomeproduto as produto_nome 
            FROM pedido p 
            JOIN cliente c ON p.idcliente = c.idcliente 
            JOIN produto pr ON p.idproduto = pr.idproduto
            ORDER BY p.idpedido DESC
            """;
        
        try (Connection conn = ConexaoFactory.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdpedido(rs.getInt("idpedido"));
                pedido.setIdcliente(rs.getInt("idcliente"));
                pedido.setClienteNome(rs.getString("cliente_nome"));
                pedido.setIdproduto(rs.getInt("idproduto"));
                pedido.setProdutoNome(rs.getString("produto_nome"));
                pedido.setQtd(rs.getInt("qtd"));
                pedido.setData(rs.getDate("data"));
                pedido.setTotal(rs.getDouble("total"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }
    
    public Pedido buscarPorId(int idpedido) throws SQLException {
        String sql = """
            SELECT p.*, c.nome as cliente_nome, pr.nomeproduto as produto_nome 
            FROM pedido p 
            JOIN cliente c ON p.idcliente = c.idcliente 
            JOIN produto pr ON p.idproduto = pr.idproduto
            WHERE p.idpedido = ?
            """;
        
        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idpedido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setIdpedido(rs.getInt("idpedido"));
                    pedido.setIdcliente(rs.getInt("idcliente"));
                    pedido.setClienteNome(rs.getString("cliente_nome"));
                    pedido.setIdproduto(rs.getInt("idproduto"));
                    pedido.setProdutoNome(rs.getString("produto_nome"));
                    pedido.setQtd(rs.getInt("qtd"));
                    pedido.setData(rs.getDate("data"));
                    pedido.setTotal(rs.getDouble("total"));
                    return pedido;
                }
            }
        }
        return null;
    }
    
    public void atualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET qtd=?, data=?, total=?, idcliente=?, idproduto=? WHERE idpedido=?";
        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getQtd());
            stmt.setDate(2, new java.sql.Date(pedido.getData().getTime()));
            stmt.setDouble(3, pedido.getTotal());
            stmt.setInt(4, pedido.getIdcliente());
            stmt.setInt(5, pedido.getIdproduto());
            stmt.setInt(6, pedido.getIdpedido());
            stmt.executeUpdate();
        }
    }
    
    public void deletar(int idpedido) throws SQLException {
        String sql = "DELETE FROM pedido WHERE idpedido=?";
        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idpedido);
            stmt.executeUpdate();
        }
    }
    
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM pedido";
        try (Connection conn = ConexaoFactory.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}

