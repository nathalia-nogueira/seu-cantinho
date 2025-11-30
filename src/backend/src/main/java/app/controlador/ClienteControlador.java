package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Cliente;
import app.servico.ClienteServico; 

public class ClienteControlador {
    private static final ClienteServico clienteServico = new ClienteServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/clientes", () -> {
            get("", (req, res) -> gson.toJson(clienteServico.listar()));

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Cliente cliente = (Cliente) clienteServico.buscarPorId(id);
                if (cliente == null) {
                    res.status(404);
                    return "{\"erro\": \"Cliente não encontrado\"}";
                }
                return gson.toJson(cliente);
            });

            post("", (req, res) -> {
                Cliente cliente = gson.fromJson(req.body(), Cliente.class);
                clienteServico.salvar(cliente);
                res.status(201);
                return gson.toJson(cliente);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Cliente cliente = gson.fromJson(req.body(), Cliente.class);
                cliente.setId(id);
                clienteServico.atualizar(cliente);
                return gson.toJson(cliente);
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                clienteServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
