package mensal04.dao;

import mensal04.model.Cliente;
import mensal04.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ClienteDAO {

    // ---------------------------------------------------
    // SALVAR CLIENTE
    // ---------------------------------------------------
    public void salvar(Cliente c) {

        String sql = "INSERT INTO cliente (nome, cpf, rg, endereco_id, foto_base64) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCpf());
            stmt.setString(3, c.getRg());
            stmt.setInt(4, c.getEndereco().getId());

            // Converte byte[] → base64
            if (c.getFoto() != null) {
                String base64 = Base64.getEncoder().encodeToString(c.getFoto());
                stmt.setString(5, base64);
            } else {
                stmt.setString(5, null);
            }

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    // ---------------------------------------------------
    // LISTAR TODOS
    // ---------------------------------------------------
    public List<Cliente> listarTodos() {

        String sql = """
                SELECT c.id, c.nome, c.cpf, c.rg, c.foto_base64,
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

                // base64 → byte[]
                String base64 = rs.getString("foto_base64");
                if (base64 != null) {
                    c.setFoto(Base64.getDecoder().decode(base64));
                }

                lista.add(c);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }

        return lista;
    }

    // ---------------------------------------------------
    // BUSCAR POR NOME
    // ---------------------------------------------------
    public List<Cliente> buscarPorNome(String nome) {

        String sql = """
                SELECT c.id, c.nome, c.cpf, c.rg, c.foto_base64,
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

                String base64 = rs.getString("foto_base64");
                if (base64 != null) {
                    c.setFoto(Base64.getDecoder().decode(base64));
                }

                lista.add(c);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar clientes: " + e.getMessage());
        }

        return lista;
    }

    // ---------------------------------------------------
    // REMOVER
    // ---------------------------------------------------
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

    // ---------------------------------------------------
    // ATUALIZAR CLIENTE
    // ---------------------------------------------------
    public void atualizar(Cliente c) {

        String sql = "UPDATE cliente SET nome=?, cpf=?, rg=?, endereco_id=?, foto_base64=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCpf());
            stmt.setString(3, c.getRg());
            stmt.setInt(4, c.getEndereco().getId());

            // foto
            if (c.getFoto() != null) {
                String base64 = Base64.getEncoder().encodeToString(c.getFoto());
                stmt.setString(5, base64);
            } else {
                stmt.setString(5, null);
            }

            stmt.setInt(6, c.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // ---------------------------------------------------
    // BUSCAR POR ID
    // ---------------------------------------------------
    public Cliente buscarPorId(int id) {

        String sql = """
            SELECT c.id, c.nome, c.cpf, c.rg, c.foto_base64,
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

                String base64 = rs.getString("foto_base64");
                if (base64 != null) {
                    c.setFoto(Base64.getDecoder().decode(base64));
                }

                return c;
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
        }

        return null;
    }
}
