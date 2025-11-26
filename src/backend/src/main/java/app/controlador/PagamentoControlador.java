package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Pagamento;
import app.servico.PagamentoServico;

public class PagamentoControlador {
    private static final PagamentoServico pagamentoServico = new PagamentoServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/pagamentos", () -> {
            get("", (req, res) -> {
                return gson.toJson(pagamentoServico.listar());
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Pagamento pagamento = pagamentoServico.buscarPorId(id);
                if (pagamento == null) {
                    res.status(404);
                    return "{\"erro\": \"Usuário não encontrado\"}";
                }
                return gson.toJson(pagamento);
            });

            post("", (req, res) -> {
                Pagamento pagamento = gson.fromJson(req.body(), Pagamento.class);
                pagamentoServico.salvar(pagamento);
                res.status(201);
                return gson.toJson(pagamento);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Pagamento pagamento = gson.fromJson(req.body(), Pagamento.class);
                pagamento.setId(id);
                pagamentoServico.atualizar(pagamento);
                return gson.toJson(pagamento);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Pagamento dadosNovos = gson.fromJson(req.body(), Pagamento.class);
                pagamentoServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(pagamentoServico.buscarPorId(id));
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                pagamentoServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
