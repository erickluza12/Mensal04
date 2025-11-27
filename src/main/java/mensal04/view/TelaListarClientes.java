package mensal04.view;

import mensal04.dao.ClienteDAO;
import mensal04.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarClientes extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField campoBusca;

    public TelaListarClientes() {
        setTitle("Listar / Remover Clientes");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        carregarTabela("");
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel superior com campo de busca
        JPanel painelTopo = new JPanel(new BorderLayout());
        campoBusca = new JTextField();
        JButton botaoBuscar = new JButton("Buscar");

        painelTopo.add(new JLabel("Buscar cliente por nome: "), BorderLayout.WEST);
        painelTopo.add(campoBusca, BorderLayout.CENTER);
        painelTopo.add(botaoBuscar, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // Tabela
        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Endereço"}, 0);
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel inferior com botão remover
        JPanel painelInferior = new JPanel();
        JButton botaoRemover = new JButton("Remover Selecionado");
        painelInferior.add(botaoRemover);

        add(painelInferior, BorderLayout.SOUTH);

        // Eventos
        //Filtra a pesquisa em tempo real
        campoBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                carregarTabela(campoBusca.getText());
            }
        });
        //Remove cliente selecionado
        botaoRemover.addActionListener(e -> removerSelecionado());
    }

    private void carregarTabela(String filtro) {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> lista = dao.buscarPorNome(filtro);

        modelo.setRowCount(0); // limpar tabela

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getCpf(),
                    c.getEndereco()
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

        String cpf = tabela.getValueAt(linha, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja remover o cliente?",
                "Confirmar remoção",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            ClienteDAO dao = new ClienteDAO();
            dao.removerPorCPF(cpf);
            carregarTabela(campoBusca.getText()); // recarrega tabela
        }
    }
}
