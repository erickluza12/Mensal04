package mensal04.view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema Mensal 04");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnCadastrar = new JButton("Cadastrar Cliente");
        JButton btnListar = new JButton("Listar Clientes");
        JButton btnSair = new JButton("Sair");

        add(btnCadastrar);
        add(btnListar);
        add(btnSair);

        btnCadastrar.addActionListener(e -> {
            TelaCadastrarCliente tela = new TelaCadastrarCliente();
            tela.setVisible(true);
        });

        btnListar.addActionListener(e -> {
            TelaListarClientes tela = new TelaListarClientes();
            tela.setVisible(true);
        });

        btnSair.addActionListener(e -> System.exit(0));
    }
}
