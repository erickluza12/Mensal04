package mensal04.view;

import mensal04.dao.ClienteDAO;
import mensal04.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarClientes extends JFrame {

    JTable tabela;
    private DefaultTableModel modelo;
    JTextField campoBusca;

    public TelaListarClientes() {
        setTitle("Listar Clientes");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        carregarTabela("");
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel superior (busca)
        JPanel painelTopo = new JPanel(new BorderLayout());
        campoBusca = new JTextField();
        JButton botaoBuscar = new JButton("Buscar");

        painelTopo.add(new JLabel("Buscar cliente por nome: "), BorderLayout.WEST);
        painelTopo.add(campoBusca, BorderLayout.CENTER);
        painelTopo.add(botaoBuscar, BorderLayout.EAST);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela não editável
        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Endereço"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel painelInferior = new JPanel();

        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");

        painelInferior.add(btnEditar);
        painelInferior.add(btnRemover);

        add(painelInferior, BorderLayout.SOUTH);

        // Eventos
        campoBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                carregarTabela(campoBusca.getText());
            }
        });

        botaoBuscar.addActionListener(e -> carregarTabela(campoBusca.getText()));
        btnRemover.addActionListener(e -> removerSelecionado());
        btnEditar.addActionListener(e -> editarSelecionado());
    }

    public void carregarTabela(String filtro) {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> lista = dao.buscarPorNome(filtro);

        modelo.setRowCount(0);

        for (Cliente c : lista) {

            String enderecoFmt =
                    c.getEndereco().getRua() + ", " +
                            c.getEndereco().getBairro() + ", " +
                            c.getEndereco().getCidade() +
                            " - CEP " + c.getEndereco().getCep();

            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getCpf(),
                    enderecoFmt
            });
        }
    }

    private void removerSelecionado() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um cliente na tabela!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(tabela.getValueAt(linha, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja remover o cliente ID " + id + "?",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            ClienteDAO dao = new ClienteDAO();
            dao.remover(id);
            carregarTabela(campoBusca.getText());
        }
    }

    private void editarSelecionado() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um cliente para editar!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(tabela.getValueAt(linha, 0).toString());

        TelaEditarCliente telaEdicao = new TelaEditarCliente(id, this);
        telaEdicao.setVisible(true);
    }
}
