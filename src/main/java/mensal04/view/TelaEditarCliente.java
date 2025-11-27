package mensal04.view;

import mensal04.dao.ClienteDAO;
import mensal04.dao.EnderecoDAO;
import mensal04.model.Cliente;
import mensal04.model.Endereco;
import mensal04.service.ViaCEPService;

import javax.swing.*;
import java.awt.*;

public class TelaEditarCliente extends JFrame {

    private JTextField campoNome;
    private JTextField campoCPF;
    private JTextField campoRG;
    private JTextField campoCEP;
    private JTextField campoRua;
    private JTextField campoBairro;
    private JTextField campoCidade;

    private int clienteId;
    private Cliente clienteOriginal;
    private TelaListarClientes telaPai;

    public TelaEditarCliente(int idCliente, TelaListarClientes telaPai) {
        this.clienteId = idCliente;
        this.telaPai = telaPai;

        setTitle("Editar Cliente");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        carregarDados();
    }

    private void initComponents() {
        setLayout(new GridLayout(10, 2, 5, 5));

        add(new JLabel("Nome:"));
        campoNome = new JTextField();
        add(campoNome);

        add(new JLabel("CPF:"));
        campoCPF = new JTextField();
        add(campoCPF);

        add(new JLabel("RG:"));
        campoRG = new JTextField();
        add(campoRG);

        add(new JLabel("CEP:"));
        JPanel painelCEP = new JPanel(new BorderLayout());
        campoCEP = new JTextField();
        JButton btnBuscarCEP = new JButton("Buscar");
        painelCEP.add(campoCEP, BorderLayout.CENTER);
        painelCEP.add(btnBuscarCEP, BorderLayout.EAST);
        add(painelCEP);

        add(new JLabel("Rua:"));
        campoRua = new JTextField();
        campoRua.setEditable(false);
        add(campoRua);

        add(new JLabel("Bairro:"));
        campoBairro = new JTextField();
        campoBairro.setEditable(false);
        add(campoBairro);

        add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        campoCidade.setEditable(false);
        add(campoCidade);

        add(new JLabel(""));
        JButton botaoAtualizar = new JButton("Atualizar Cliente");
        add(botaoAtualizar);

        btnBuscarCEP.addActionListener(e -> buscarCEP());
        botaoAtualizar.addActionListener(e -> atualizarCliente());
    }

    private void carregarDados() {
        ClienteDAO dao = new ClienteDAO();
        clienteOriginal = dao.buscarPorId(clienteId);

        if (clienteOriginal == null) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cliente!");
            dispose();
            return;
        }

        campoNome.setText(clienteOriginal.getNome());
        campoCPF.setText(clienteOriginal.getCpf());
        campoRG.setText(clienteOriginal.getRg());

        Endereco e = clienteOriginal.getEndereco();
        campoCEP.setText(e.getCep());
        campoRua.setText(e.getRua());
        campoBairro.setText(e.getBairro());
        campoCidade.setText(e.getCidade());
    }

    private void buscarCEP() {
        try {
            Endereco e = ViaCEPService.buscarCEP(campoCEP.getText());

            campoRua.setText(e.getRua());
            campoBairro.setText(e.getBairro());
            campoCidade.setText(e.getCidade());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "CEP inválido!");
        }
    }

    private void atualizarCliente() {
        try {
            // Atualiza endereço
            Endereco endereco = clienteOriginal.getEndereco();
            endereco.setRua(campoRua.getText());
            endereco.setBairro(campoBairro.getText());
            endereco.setCidade(campoCidade.getText());
            endereco.setCep(campoCEP.getText());

            new EnderecoDAO().atualizar(endereco);

            // Atualiza cliente
            clienteOriginal.setNome(campoNome.getText());
            clienteOriginal.setCpf(campoCPF.getText());
            clienteOriginal.setRg(campoRG.getText());

            new ClienteDAO().atualizar(clienteOriginal);

            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");

            // RECARREGA A LISTA AUTOMATICAMENTE
            telaPai.carregarTabela(telaPai.campoBusca.getText());

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }
}
