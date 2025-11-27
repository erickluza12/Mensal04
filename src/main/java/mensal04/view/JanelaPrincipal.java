package mensal04.view;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal() {
        setTitle("Sistema Mensal 04");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JButton botaoCadastrarCliente = new JButton("Cadastrar Cliente");

        // abre a tela de cadastro
        botaoCadastrarCliente.addActionListener(e ->
                new TelaCadastrarCliente().setVisible(true)
        );

        setLayout(new FlowLayout());
        add(botaoCadastrarCliente);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }
}
