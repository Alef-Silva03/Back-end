package interfacegui.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            // Verifique se o nome do banco, user e senha coincidem com o seu MySQL
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/interface", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar: " + e);
        }
    }
}