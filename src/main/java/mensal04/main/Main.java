package mensal04.main;

import mensal04.view.TelaLogin;
import mensal04.view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {

        TelaLogin login = new TelaLogin(null);
        login.setVisible(true);

        if (login.isAutenticado()) {
            new TelaPrincipal().setVisible(true);
        }
    }
}
