package mensal04.service;

import mensal04.model.Endereco;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class ViaCEPService {

    private static final OkHttpClient client = new OkHttpClient();

    public static Endereco buscarCEP(String cep) throws Exception {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        Request req = new Request.Builder().url(url).build();
        Response resp = client.newCall(req).execute();

        if (!resp.isSuccessful()) {
            throw new Exception("Erro ao consultar CEP");
        }

        String json = resp.body().string();
        JSONObject obj = new JSONObject(json);

        if (obj.has("erro")) {
            throw new Exception("CEP não encontrado");
        }

        Endereco e = new Endereco();
        e.setRua(obj.getString("logradouro"));
        e.setBairro(obj.getString("bairro"));
        e.setCidade(obj.getString("localidade"));
        e.setCep(obj.getString("cep"));

        return e;
    }
}
