package mensal04.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/mensal04?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";         // ALTERE AQUI se seu MySQL usar outro usuário
    private static final String PASS = "LUza6264";    // ALTERE AQUI para sua senha do MySQL

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco: " + e.getMessage());
        }
    }
}
