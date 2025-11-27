package mensal04.dao;

import mensal04.model.Endereco;
import java.sql.*;

public class EnderecoDAO {

    public int salvar(Endereco e) {
        String sql = "INSERT INTO endereco (rua, bairro, cidade, cep) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, e.getRua());
            stmt.setString(2, e.getBairro());
            stmt.setString(3, e.getCidade());
            stmt.setString(4, e.getCep());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar endereço: " + ex.getMessage());
        }

        return -1;
    }

    public void atualizar(Endereco e) {
        String sql = "UPDATE endereco SET rua=?, bairro=?, cidade=?, cep=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getRua());
            stmt.setString(2, e.getBairro());
            stmt.setString(3, e.getCidade());
            stmt.setString(4, e.getCep());
            stmt.setInt(5, e.getId());

            stmt.executeUpdate();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar endereço: " + ex.getMessage());
        }
    }
}
