package mensal04.main;

import mensal04.dao.ClienteDAO;
import mensal04.model.Cliente;

public class TesteCadastrarCliente {
    public static void main(String[] args) {

        Cliente c = new Cliente();
        c.setNome("João da Silva");
        c.setCpf("123.456.789-00");
        c.setEndereco("Rua A, 123");

        ClienteDAO dao = new ClienteDAO();
        dao.salvar(c);

        System.out.println("Cliente salvo com sucesso!");
    }
}
