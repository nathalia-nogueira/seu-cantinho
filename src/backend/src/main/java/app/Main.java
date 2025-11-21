package app;

import static spark.Spark.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import app.modelo.*;
import app.modelo.enums.*;
import app.repositorio.UsuarioRepositorio;

public class Main {
    public static void main(String[] args) {
        port(8080);
        enableCORS("*", "GET, POST, PUT, PATCH, DELETE, OPTIONS", "*");

        Gson gson = new Gson();
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();

        get("/teste-modelos", (req, res) -> {
            Cliente cliente = new Cliente();
            cliente.setNome("Nathália");
            cliente.setCpf("123.456.789-00");

            Espaco espaco = new Espaco();
            espaco.setNome("Auditório Central");

            Reserva reserva = new Reserva();
            reserva.setId(1L);
            reserva.setDataHoraInicio(LocalDateTime.now());
            reserva.setDataHoraFim(LocalDateTime.now().plusHours(2));
            reserva.setSinal(100.0);
            reserva.setValorTotal(400.0);
            reserva.setStatusReserva(StatusReserva.CONFIRMADA);
            reserva.setStatusPagamento(StatusPagamento.SINAL_PAGO);
            reserva.setCliente(cliente);
            reserva.setEspaco(espaco);
            reserva.setPagamentos(new ArrayList<>());

            return "Instâncias criadas com sucesso: "
                   + reserva.toString() + ", "
                   + cliente.getNome() + ", "
                   + espaco.getNome();
        });

        post("/usuarios", (req, res) -> {
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            usuarioRepo.salvar(usuario);
            res.status(201);
            return gson.toJson(usuario);
        });

        get("/usuarios", (req, res) -> gson.toJson(usuarioRepo.listar()));

        get("/usuarios/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Usuario usuario = usuarioRepo.buscarPorId(id);
            if (usuario == null) {
                res.status(404);
                return "{\"erro\": \"Usuário não encontrado\"}";
            }
            return gson.toJson(usuario);
        });

        put("/usuarios/:id", (req, res) -> {
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            usuario.setId(Long.parseLong(req.params(":id")));
            usuarioRepo.atualizar(usuario);
            return gson.toJson(usuario);
        });

        patch("/usuarios/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Usuario dadosNovos = gson.fromJson(req.body(), Usuario.class);
            usuarioRepo.atualizarParcial(id, dadosNovos);
            return gson.toJson(usuarioRepo.buscarPorId(id));
        });

        delete("/usuarios/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            usuarioRepo.remover(id);
            res.status(204);
            return "";
        });

        System.out.println("Servidor Spark rodando na porta 8080...");
        awaitInitialization();
    }

    private static void enableCORS(final String origin, final String methods, final String headers) {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null)
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null)
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Allow-Methods", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}
