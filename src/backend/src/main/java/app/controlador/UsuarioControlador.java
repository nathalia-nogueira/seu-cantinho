package app.controlador;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.modelo.Usuario;
import app.servico.UsuarioServico;

public class UsuarioControlador {
    private static final UsuarioServico usuarioServico = new UsuarioServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/usuarios", () -> {
            get("", (req, res) -> {
                return gson.toJson(usuarioServico.listar());
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Usuario usuario = usuarioServico.buscarPorId(id);
                if (usuario == null) {
                    res.status(404);
                    return "{\"erro\": \"Usuário não encontrado\"}";
                }
                return gson.toJson(usuario);
            });

            post("", (req, res) -> {
                Usuario usuario = gson.fromJson(req.body(), Usuario.class);
                usuarioServico.salvar(usuario);
                res.status(201);
                return gson.toJson(usuario);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Usuario usuario = gson.fromJson(req.body(), Usuario.class);
                usuario.setId(id);
                usuarioServico.atualizar(usuario);
                return gson.toJson(usuario);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Usuario dadosNovos = gson.fromJson(req.body(), Usuario.class);
                usuarioServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(usuarioServico.buscarPorId(id));
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                usuarioServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
