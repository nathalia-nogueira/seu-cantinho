package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Reserva;
import app.servico.ReservaServico;

public class ReservaControlador {
    private static final ReservaServico reservaServico = new ReservaServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/reservas", () -> {
            get("", (req, res) -> {
                return gson.toJson(reservaServico.listar());
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Reserva reserva = reservaServico.buscarPorId(id);
                if (reserva == null) {
                    res.status(404);
                    return "{\"erro\": \"Usuário não encontrado\"}";
                }
                return gson.toJson(reserva);
            });

            post("", (req, res) -> {
                Reserva reserva = gson.fromJson(req.body(), Reserva.class);
                reservaServico.salvar(reserva);
                res.status(201);
                return gson.toJson(reserva);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Reserva reserva = gson.fromJson(req.body(), Reserva.class);
                reserva.setId(id);
                reservaServico.atualizar(reserva);
                return gson.toJson(reserva);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Reserva dadosNovos = gson.fromJson(req.body(), Reserva.class);
                reservaServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(reservaServico.buscarPorId(id));
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                reservaServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
