package mensal04.dao;

import mensal04.model.Cliente;
import mensal04.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // -----------------------------
    // SALVAR
    // -----------------------------
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

    // -----------------------------
    // LISTAR TODOS
    // -----------------------------
    public List<Cliente> listarTodos() {

        String sql = """
                SELECT c.id, c.nome, c.cpf, c.rg,
                       e.id AS eid, e.rua, e.bairro, e.cidade, e.cep
                FROM cliente c
                JOIN endereco e ON c.endereco_id = e.id
                """;

        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }

        return lista;
    }

    // -----------------------------
    // BUSCAR POR NOME
    // -----------------------------
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

    // -----------------------------
    // REMOVER
    // -----------------------------
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

    // -----------------------------
    // ATUALIZAR
    // -----------------------------
    public void atualizar(Cliente c) {

        String sql = "UPDATE cliente SET nome=?, cpf=?, rg=?, endereco_id=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCpf());
            stmt.setString(3, c.getRg());
            stmt.setInt(4, c.getEndereco().getId());
            stmt.setInt(5, c.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // -----------------------------
    // BUSCAR POR ID
    // -----------------------------
    public Cliente buscarPorId(int id) {

        String sql = """
            SELECT c.id, c.nome, c.cpf, c.rg,
                   e.id AS eid, e.rua, e.bairro, e.cidade, e.cep
            FROM cliente c
            JOIN endereco e ON c.endereco_id = e.id
            WHERE c.id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

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

                return c;
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
        }

        return null;
    }
}
