package mensal04.view;

import mensal04.dao.UsuarioDAO;
import mensal04.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JDialog {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    private boolean autenticado = false;

    public TelaLogin(JFrame parent) {
        super(parent, "Login", true); // modal
        setSize(350, 220);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnCriarConta = new JButton("Criar Conta");
        JButton btnSair = new JButton("Sair");

        add(btnEntrar);
        add(btnCriarConta);
        add(btnSair);

        btnEntrar.addActionListener(e -> autenticar());

        btnCriarConta.addActionListener(e -> {
            TelaCadastroUsuario cad = new TelaCadastroUsuario(this);
            cad.setVisible(true);
        });

        btnSair.addActionListener(e -> System.exit(0));
    }

    private void autenticar() {

        String usuario = campoUsuario.getText().trim();
        String senhaDigitada = new String(campoSenha.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.buscarPorNome(usuario);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
            return;
        }

        if (!u.getStatus().equals("ATIVO")) {
            JOptionPane.showMessageDialog(this, "Usuário está inativo!");
            return;
        }

        // >>> VERIFICAÇÃO REAL DE SENHA COM BCrypt <<<
        if (BCrypt.checkpw(senhaDigitada, u.getSenha())) {
            autenticado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
        }
    }

    public boolean isAutenticado() {
        return autenticado;
    }
}
