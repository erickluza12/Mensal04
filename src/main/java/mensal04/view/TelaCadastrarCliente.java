package mensal04.view;

import mensal04.dao.ClienteDAO;
import mensal04.dao.EnderecoDAO;
import mensal04.model.Cliente;
import mensal04.model.Endereco;
import mensal04.service.ViaCEPService;

import javax.swing.*;
import java.awt.*;

public class TelaCadastrarCliente extends JFrame {

    private JTextField campoNome;
    private JFormattedTextField campoCPF;
    private JFormattedTextField campoRG;
    private JTextField campoCEP;
    private JTextField campoRua;
    private JTextField campoBairro;
    private JTextField campoCidade;

    public TelaCadastrarCliente() {
        setTitle("Cadastrar Cliente");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(11, 2, 5, 5));

        //------------------------
        // LINHA 1 - Nome
        //------------------------
        add(new JLabel("Nome:"));
        campoNome = new JTextField();
        add(campoNome);

        //------------------------
        // LINHA 2 - CPF (com máscara)
        //------------------------
        add(new JLabel("CPF:"));
        try {
            javax.swing.text.MaskFormatter maskCPF = new javax.swing.text.MaskFormatter("###.###.###-##");
            maskCPF.setPlaceholderCharacter('_');
            campoCPF = new JFormattedTextField(maskCPF);
        } catch (Exception e) {
            campoCPF = new JFormattedTextField();
        }
        add(campoCPF);

        //------------------------
        // LINHA 3 - RG (com máscara)
        //------------------------
        add(new JLabel("RG:"));
        try {
            javax.swing.text.MaskFormatter maskRG = new javax.swing.text.MaskFormatter("##.###.###-#");
            maskRG.setPlaceholderCharacter('_');
            campoRG = new JFormattedTextField(maskRG);
        } catch (Exception e) {
            campoRG = new JFormattedTextField();
        }
        add(campoRG);

        //------------------------
        // LINHA 4 - CEP + botão
        //------------------------
        add(new JLabel("CEP:"));

        JPanel painelCEP = new JPanel(new BorderLayout());
        campoCEP = new JTextField();
        JButton btnBuscarCEP = new JButton("Buscar");

        painelCEP.add(campoCEP, BorderLayout.CENTER);
        painelCEP.add(btnBuscarCEP, BorderLayout.EAST);
        add(painelCEP);

        //------------------------
        // LINHA 5 - Rua
        //------------------------
        add(new JLabel("Rua:"));
        campoRua = new JTextField();
        campoRua.setEditable(false);
        add(campoRua);

        //------------------------
        // LINHA 6 - Bairro
        //------------------------
        add(new JLabel("Bairro:"));
        campoBairro = new JTextField();
        campoBairro.setEditable(false);
        add(campoBairro);

        //------------------------
        // LINHA 7 - Cidade
        //------------------------
        add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        campoCidade.setEditable(false);
        add(campoCidade);

        //------------------------
        // LINHA 8 - Espaço
        //------------------------
        add(new JLabel(""));
        add(new JLabel(""));

        //------------------------
        // LINHA 9 - Botões
        //------------------------
        add(new JLabel("")); // alinhamento

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton botaoSalvar = new JButton("Salvar Cliente");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        add(painelBotoes);

        //------------------------
        // EVENTOS
        //------------------------
        btnBuscarCEP.addActionListener(e -> buscarCEP());
        botaoSalvar.addActionListener(e -> salvarCliente());
        botaoCancelar.addActionListener(e -> dispose());
    }

    private void buscarCEP() {
        String cep = campoCEP.getText().trim();

        if (cep.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um CEP!");
            return;
        }

        try {
            Endereco endereco = ViaCEPService.buscarCEP(cep);

            campoRua.setText(endereco.getRua());
            campoBairro.setText(endereco.getBairro());
            campoCidade.setText(endereco.getCidade());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "CEP inválido ou não encontrado!");
        }
    }

    private void salvarCliente() {

        if (campoNome.getText().trim().isEmpty() ||
                campoCPF.getText().contains("_") ||
                campoRG.getText().contains("_") ||
                campoCEP.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
            return;
        }

        try {
            // Salvar endereço
            Endereco e = new Endereco();
            e.setRua(campoRua.getText());
            e.setBairro(campoBairro.getText());
            e.setCidade(campoCidade.getText());
            e.setCep(campoCEP.getText());

            EnderecoDAO endDAO = new EnderecoDAO();
            int enderecoID = endDAO.salvar(e);
            e.setId(enderecoID);

            // Criar cliente
            Cliente c = new Cliente();
            c.setNome(campoNome.getText());
            c.setCpf(campoCPF.getText());
            c.setRg(campoRG.getText());
            c.setEndereco(e);

            // Salvar cliente
            ClienteDAO cliDAO = new ClienteDAO();
            cliDAO.salvar(c);

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar cliente: " + ex.getMessage());
        }
    }
}
