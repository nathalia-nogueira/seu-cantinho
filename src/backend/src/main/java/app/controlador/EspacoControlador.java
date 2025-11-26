package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Espaco;
import app.servico.EspacoServico;

public class EspacoControlador {
    private static final EspacoServico espacoServico = new EspacoServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/espacos", () -> {
            get("", (req, res) -> {
                return gson.toJson(espacoServico.listar());
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Espaco espaco = espacoServico.buscarPorId(id);
                if (espaco == null) {
                    res.status(404);
                    return "{\"erro\": \"Usuário não encontrado\"}";
                }
                return gson.toJson(espaco);
            });

            post("", (req, res) -> {
                Espaco espaco = gson.fromJson(req.body(), Espaco.class);
                espacoServico.salvar(espaco);
                res.status(201);
                return gson.toJson(espaco);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Espaco espaco = gson.fromJson(req.body(), Espaco.class);
                espaco.setId(id);
                espacoServico.atualizar(espaco);
                return gson.toJson(espaco);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Espaco dadosNovos = gson.fromJson(req.body(), Espaco.class);
                espacoServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(espacoServico.buscarPorId(id));
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                espacoServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
