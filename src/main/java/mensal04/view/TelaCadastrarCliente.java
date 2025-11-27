package mensal04.view;

import mensal04.dao.ClienteDAO;
import mensal04.dao.EnderecoDAO;
import mensal04.model.Cliente;
import mensal04.model.Endereco;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class TelaCadastrarCliente extends JFrame {

    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoRg;
    private JTextField campoCep;
    private JTextField campoRua;
    private JTextField campoBairro;
    private JTextField campoCidade;

    public TelaCadastrarCliente() {

        setTitle("Cadastrar Cliente");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {

        JPanel painel = new JPanel(new GridLayout(8, 2, 10, 10));

        painel.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painel.add(campoNome);

        painel.add(new JLabel("CPF:"));
        campoCpf = new JTextField();
        painel.add(campoCpf);

        painel.add(new JLabel("RG:"));
        campoRg = new JTextField();
        painel.add(campoRg);

        painel.add(new JLabel("CEP:"));
        JPanel painelCep = new JPanel(new BorderLayout());
        campoCep = new JTextField();
        JButton btnBuscarCep = new JButton("Buscar");
        painelCep.add(campoCep, BorderLayout.CENTER);
        painelCep.add(btnBuscarCep, BorderLayout.EAST);
        painel.add(painelCep);

        painel.add(new JLabel("Rua:"));
        campoRua = new JTextField();
        campoRua.setEditable(false);
        painel.add(campoRua);

        painel.add(new JLabel("Bairro:"));
        campoBairro = new JTextField();
        campoBairro.setEditable(false);
        painel.add(campoBairro);

        painel.add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        campoCidade.setEditable(false);
        painel.add(campoCidade);

        JButton btnSalvar = new JButton("Salvar");
        painel.add(btnSalvar);

        add(painel);

        // EVENTOS
        btnBuscarCep.addActionListener(e -> buscarCep());
        btnSalvar.addActionListener(e -> salvarCliente());
    }

    private void buscarCep() {

        String cep = campoCep.getText().replace("-", "").trim();

        if (cep.length() != 8) {
            JOptionPane.showMessageDialog(this, "CEP inválido!");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://viacep.com.br/ws/" + cep + "/json/")
                    .build();

            Response response = client.newCall(request).execute();

            String body = response.body().string();

            JSONObject json = new JSONObject(body);

            if (json.has("erro")) {
                JOptionPane.showMessageDialog(this, "CEP não encontrado!");
                return;
            }

            campoRua.setText(json.getString("logradouro"));
            campoBairro.setText(json.getString("bairro"));
            campoCidade.setText(json.getString("localidade"));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar CEP: " + ex.getMessage());
        }
    }

    private void salvarCliente() {

        try {
            // Endereco
            Endereco e = new Endereco();
            e.setCep(campoCep.getText());
            e.setRua(campoRua.getText());
            e.setBairro(campoBairro.getText());
            e.setCidade(campoCidade.getText());

            EnderecoDAO enderecoDAO = new EnderecoDAO();
            int enderecoId = enderecoDAO.salvar(e);

            if (enderecoId == -1) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar endereço!");
                return;
            }

            e.setId(enderecoId);

            // Cliente
            Cliente c = new Cliente();
            c.setNome(campoNome.getText());
            c.setCpf(campoCpf.getText());
            c.setRg(campoRg.getText());
            c.setEndereco(e);

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.salvar(c);

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage());
        }
    }
}
