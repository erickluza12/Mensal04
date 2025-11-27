package mensal04.view;

import mensal04.dao.UsuarioDAO;
import mensal04.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JDialog {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public TelaCadastroUsuario(JDialog parent) {
        super(parent, "Criar Usuário", true);
        setSize(300, 180);
        setLocationRelativeTo(parent);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Novo usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Nova senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnSalvar);
        add(btnCancelar);

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvar() {
        String usuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        try {
            Usuario u = new Usuario();
            u.setNome(usuario);
            u.setSenha(senha); // DAO vai fazer o hash

            UsuarioDAO dao = new UsuarioDAO();
            dao.salvar(u);

            JOptionPane.showMessageDialog(this, "Usuário criado com sucesso!");
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar usuário: " + e.getMessage());
        }
    }
}
