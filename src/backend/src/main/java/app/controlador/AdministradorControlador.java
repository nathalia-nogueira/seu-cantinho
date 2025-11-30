package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Administrador;
import app.servico.AdministradorServico; 

public class AdministradorControlador {
    private static final AdministradorServico administradorServico = new AdministradorServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/administradores", () -> {
            get("", (req, res) -> gson.toJson(administradorServico.listar()));

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Administrador administrador = (Administrador) administradorServico.buscarPorId(id);
                if (administrador == null) {
                    res.status(404);
                    return "{\"erro\": \"Administrador não encontrado\"}";
                }
                return gson.toJson(administrador);
            });

            post("", (req, res) -> {
                Administrador administrador = gson.fromJson(req.body(), Administrador.class);
                administradorServico.salvar(administrador);
                res.status(201);
                return gson.toJson(administrador);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Administrador administrador = gson.fromJson(req.body(), Administrador.class);
                administrador.setId(id);
                administradorServico.atualizar(administrador);
                return gson.toJson(administrador);
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                administradorServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
