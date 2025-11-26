package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Endereco;
import app.servico.EnderecoServico;

public class EnderecoControlador {
    private static final EnderecoServico enderecoServico = new EnderecoServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/enderecos", () -> {
            get("", (req, res) -> {
                return gson.toJson(enderecoServico.listar());
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Endereco endereco = enderecoServico.buscarPorId(id);
                if (endereco == null) {
                    res.status(404);
                    return "{\"erro\": \"Usuário não encontrado\"}";
                }
                return gson.toJson(endereco);
            });

            post("", (req, res) -> {
                Endereco endereco = gson.fromJson(req.body(), Endereco.class);
                enderecoServico.salvar(endereco);
                res.status(201);
                return gson.toJson(endereco);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Endereco endereco = gson.fromJson(req.body(), Endereco.class);
                endereco.setId(id);
                enderecoServico.atualizar(endereco);
                return gson.toJson(endereco);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Endereco dadosNovos = gson.fromJson(req.body(), Endereco.class);
                enderecoServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(enderecoServico.buscarPorId(id));
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                enderecoServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
