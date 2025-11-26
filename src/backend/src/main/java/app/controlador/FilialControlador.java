package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Filial;
import app.servico.FilialServico;

public class FilialControlador {
    private static final FilialServico filialServico = new FilialServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/filiais", () -> {
            get("", (req, res) -> {
                return gson.toJson(filialServico.listar());
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Filial filial = filialServico.buscarPorId(id);
                if (filial == null) {
                    res.status(404);
                    return "{\"erro\": \"Usuário não encontrado\"}";
                }
                return gson.toJson(filial);
            });

            post("", (req, res) -> {
                Filial filial = gson.fromJson(req.body(), Filial.class);
                filialServico.salvar(filial);
                res.status(201);
                return gson.toJson(filial);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Filial filial = gson.fromJson(req.body(), Filial.class);
                filial.setId(id);
                filialServico.atualizar(filial);
                return gson.toJson(filial);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Filial dadosNovos = gson.fromJson(req.body(), Filial.class);
                filialServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(filialServico.buscarPorId(id));
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                filialServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
