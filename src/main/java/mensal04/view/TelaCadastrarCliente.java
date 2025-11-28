package mensal04.view;

import mensal04.dao.ClienteDAO;
import mensal04.dao.EnderecoDAO;
import mensal04.model.Cliente;
import mensal04.model.Endereco;
import mensal04.service.ViaCEPService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class TelaCadastrarCliente extends JFrame {

    private JTextField campoNome;
    private JFormattedTextField campoCPF;
    private JTextField campoRG;
    private JTextField campoCEP;
    private JTextField campoRua;
    private JTextField campoBairro;
    private JTextField campoCidade;
    private byte[] fotoSelecionada = null; // foto em bytes

    public TelaCadastrarCliente() {
        setTitle("Cadastrar Cliente");
        setSize(450, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(12, 2, 5, 5));

        // Nome
        add(new JLabel("Nome:"));
        campoNome = new JTextField();
        add(campoNome);

        // CPF
        add(new JLabel("CPF:"));
        try {
            javax.swing.text.MaskFormatter maskCPF = new javax.swing.text.MaskFormatter("###.###.###-##");
            maskCPF.setPlaceholderCharacter('_');
            campoCPF = new JFormattedTextField(maskCPF);
        } catch (Exception e) {
            campoCPF = new JFormattedTextField();
        }
        add(campoCPF);

        // RG
        add(new JLabel("RG:"));
        try {
            javax.swing.text.MaskFormatter maskRG = new javax.swing.text.MaskFormatter("##.###.###-#");
            maskRG.setPlaceholderCharacter('_');
            campoRG = new JFormattedTextField(maskRG);
        } catch (Exception e) {
            campoRG = new JFormattedTextField();
        }
        add(campoRG);


        // CEP
        add(new JLabel("CEP:"));
        JPanel painelCEP = new JPanel(new BorderLayout());
        campoCEP = new JTextField();
        JButton btnBuscarCEP = new JButton("Buscar CEP");
        painelCEP.add(campoCEP, BorderLayout.CENTER);
        painelCEP.add(btnBuscarCEP, BorderLayout.EAST);
        add(painelCEP);

        // Rua
        add(new JLabel("Rua:"));
        campoRua = new JTextField();
        campoRua.setEditable(false);
        add(campoRua);

        // Bairro
        add(new JLabel("Bairro:"));
        campoBairro = new JTextField();
        campoBairro.setEditable(false);
        add(campoBairro);

        // Cidade
        add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        campoCidade.setEditable(false);
        add(campoCidade);

        // Foto
        add(new JLabel("Foto do Cliente:"));
        JButton btnFoto = new JButton("Selecionar Foto");
        add(btnFoto);

        // Espaço
        add(new JLabel(""));
        add(new JLabel(""));

        // Botões finais
        JButton btnSalvar = new JButton("Salvar Cliente");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnSalvar);
        add(btnCancelar);

        // EVENTOS
        btnBuscarCEP.addActionListener(e -> buscarCEP());
        btnFoto.addActionListener(e -> selecionarFoto());
        btnSalvar.addActionListener(e -> salvarCliente());
        btnCancelar.addActionListener(e -> dispose());
    }

    // ========================================
    // SELECIONAR FOTO
    // ========================================
    private void selecionarFoto() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            try {
                fotoSelecionada = Files.readAllBytes(arquivo.toPath());
                JOptionPane.showMessageDialog(this, "Foto carregada com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar foto: " + e.getMessage());
            }
        }
    }

    // ========================================
    // BUSCAR CEP
    // ========================================
    private void buscarCEP() {
        try {
            Endereco endereco = ViaCEPService.buscarCEP(campoCEP.getText().trim());
            campoRua.setText(endereco.getRua());
            campoBairro.setText(endereco.getBairro());
            campoCidade.setText(endereco.getCidade());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "CEP inválido!");
        }
    }

    // ========================================
    // SALVAR CLIENTE
    // ========================================
    private void salvarCliente() {

        if (campoNome.getText().trim().isEmpty() ||
                campoCPF.getText().trim().isEmpty() ||
                campoRG.getText().trim().isEmpty() ||
                campoCEP.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
            return;
        }

        try {
            // ----- Endereço -----
            Endereco end = new Endereco();
            end.setRua(campoRua.getText());
            end.setBairro(campoBairro.getText());
            end.setCidade(campoCidade.getText());
            end.setCep(campoCEP.getText());

            EnderecoDAO endDAO = new EnderecoDAO();
            int enderecoID = endDAO.salvar(end);
            end.setId(enderecoID);

            // ----- Cliente -----
            Cliente c = new Cliente();
            c.setNome(campoNome.getText());
            c.setCpf(campoCPF.getText());
            c.setRg(campoRG.getText());
            c.setEndereco(end);
            c.setFoto(fotoSelecionada); // salva foto em byte[]

            ClienteDAO cliDAO = new ClienteDAO();
            cliDAO.salvar(c);

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage());
        }
    }
}
