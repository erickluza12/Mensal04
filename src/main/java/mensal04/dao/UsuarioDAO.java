package mensal04.dao;

import mensal04.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UsuarioDAO {

    // SALVAR USUÁRIO COM HASH BCRYPT
    public void salvar(Usuario u) {

        String sql = "INSERT INTO usuario (nome, senha, status) VALUES (?, ?, 'ATIVO')";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gerar hash seguro
            String hash = BCrypt.hashpw(u.getSenha(), BCrypt.gensalt());

            stmt.setString(1, u.getNome());
            stmt.setString(2, hash);

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    // BUSCAR USUÁRIO POR NOME (para login)
    public Usuario buscarPorNome(String nome) {

        String sql = "SELECT * FROM usuario WHERE nome = ? AND status = 'ATIVO'";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha")); // hash salvo no banco
                u.setStatus(rs.getString("status"));
                return u;
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }

        return null;
    }

    // VALIDAR LOGIN
    public boolean validarLogin(String nome, String senhaDigitada) {

        Usuario u = buscarPorNome(nome);

        if (u == null) {
            return false; // nome inexistente
        }

        // Compara senha digitada com o hash
        return BCrypt.checkpw(senhaDigitada, u.getSenha());
    }
}
