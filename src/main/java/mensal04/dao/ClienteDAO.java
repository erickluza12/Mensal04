package mensal04.dao;

import mensal04.model.Cliente;
import mensal04.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void salvar(Cliente c) {

        String sql = "INSERT INTO cliente (nome, cpf, rg, endereco_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCpf());
            stmt.setString(3, c.getRg());
            stmt.setInt(4, c.getEndereco().getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> buscarPorNome(String nome) {

        String sql = """
                SELECT c.id, c.nome, c.cpf, c.rg,
                       e.id AS eid, e.rua, e.bairro, e.cidade, e.cep
                FROM cliente c
                JOIN endereco e ON c.endereco_id = e.id
                WHERE c.nome LIKE ?
                """;

        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Endereco e = new Endereco();
                e.setId(rs.getInt("eid"));
                e.setRua(rs.getString("rua"));
                e.setBairro(rs.getString("bairro"));
                e.setCidade(rs.getString("cidade"));
                e.setCep(rs.getString("cep"));

                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setRg(rs.getString("rg"));
                c.setEndereco(e);

                lista.add(c);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar clientes: " + e.getMessage());
        }

        return lista;
    }

    public void remover(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover cliente: " + e.getMessage());
        }
    }
}
