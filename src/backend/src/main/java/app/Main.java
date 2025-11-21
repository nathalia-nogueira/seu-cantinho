package app;

import static spark.Spark.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import app.modelo.*;
import app.modelo.enums.*;
import app.repositorio.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        enableCORS("*", "GET, POST, PUT, PATCH, DELETE, OPTIONS", "*");

        Gson gson = new Gson();
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
        EnderecoRepositorio enderecoRepo = new EnderecoRepositorio();

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

        get("/usuarios", (req, res) -> {
            gson.toJson(usuarioRepo.listar())
        });

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
            Long id = Long.parseLong(req.params(":id"));
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            usuario.setId(id);
            usuarioRepo.atualizar(usuario);
            return gson.toJson(usuario);
        });

        patch("/usuarios/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Usuario dadosNovos = gson.fromJson(req.body(), Usuario.class);
            usuarioRepo.atualizarParcialmente(id, dadosNovos);
            return gson.toJson(usuarioRepo.buscarPorId(id));
        });

        delete("/usuarios/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            usuarioRepo.remover(id);
            res.status(204);
            return "";
        });

        post("/enderecos", (req, res) -> {
            Endereco endereco = gson.fromJson(req.body(), Endereco.class);
            enderecoRepo.salvar(endereco);
            res.status(201);
            return gson.toJson(endereco);
        })

        get("/enderecos", (req, res) -> {
            gson.toJson(enderecoRepo.listar())
        });

        get("/enderecos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Endereco endereco = enderecoRepo.buscarPorId(id);
            if (endereco == null) {
                res.status(404);
                return "{\"erro\": \"Endereço não encontrado\"}";
            }
            return gson.toJson(endereco);
        });

        put("/enderecos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Endereco endereco = gson.fromJson(req.body(), Endereco.class);
            endereco.setId(id);
            enderecoRepo.atualizar(endereco);
            return gson.toJson(endereco);
        });

        patch("/enderecos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Endereco dadosNovos = gson.fromJson(req.body(), Endereco.class);
            enderecoRepo.atualizarParcialmente(id, dadosNovos);
            return gson.toJson(enderecoRepo.buscarPorId(id));
        });

        delete("/enderecos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            enderecoRepo.remover(id);
            res.status(204);
            return "";
        });

        post("/filiais", (req, res) -> {
            Filial filial = gson.fromJson(req.body(), Filial.class);
            filialRepo.salvar(filial);
            res.status(201);
            return gson.toJson(filial);
        })

        get("/filiais", (req, res) -> {
            gson.toJson(filialRepo.listar())
        });

        get("/filiais/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Filial filial = filialRepo.buscarPorId(id);
            if (filial == null) {
                res.status(404);
                return "{\"erro\": \"Filial não encontrada\"}";
            }
            return gson.toJson(filial);
        });

        put("/filiais/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Filial filial = gson.fromJson(req.body(), Filial.class);
            filial.setId(id);
            filialRepo.atualizar(filial);
            return gson.toJson(filial);
        });

        patch("/filiais/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Filial dadosNovos = gson.fromJson(req.body(), Filial.class);
            filialRepo.atualizarParcialmente(id, dadosNovos);
            return gson.toJson(filialRepo.buscarPorId(id));
        });

        delete("/filiais/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            filialRepo.remover(id);
            res.status(204);
            return "";
        });

        post("/espacos", (req, res) -> {
            Espaco espaco = gson.fromJson(req.body(), Espaco.class);
            filialRepo.salvar(espaco);
            res.status(201);
            return gson.toJson(espaco);
        })

        get("/espacos", (req, res) -> {
            gson.toJson(espacoRepo.listar())
        });

        get("/espacos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Espaco espaco = espacoRepo.buscarPorId(id);
            if (espaco == null) {
                res.status(404);
                return "{\"erro\": \"Espaço não encontrado\"}";
            }
            return gson.toJson(espaco);
        });

        put("/espacos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Espaco espaco = gson.fromJson(req.body(), Espaco.class);
            espaco.setId(id);
            espacoRepo.atualizar(espaco);
            return gson.toJson(espaco);
        });

        patch("/espacos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Espaco dadosNovos = gson.fromJson(req.body(), Espaco.class);
            espacoRepo.atualizarParcialmente(id, dadosNovos);
            return gson.toJson(espacoRepo.buscarPorId(id));
        });

        delete("/espacos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            espacoRepo.remover(id);
            res.status(204);
            return "";
        });

        post("/reservas", (req, res) -> {
            Reserva reserva = gson.fromJson(req.body(), Reserva.class);
            reservaRepo.salvar(reserva);
            res.status(201);
            return gson.toJson(reserva);
        })

        get("/reservas", (req, res) -> {
            gson.toJson(reservaRepo.listar())
        });

        get("/reservas/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Reserva reserva = reservaRepo.buscarPorId(id);
            if (reserva == null) {
                res.status(404);
                return "{\"erro\": \"Reserva não encontrada\"}";
            }
            return gson.toJson(reserva);
        });

        put("/reservas/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Reserva reserva = gson.fromJson(req.body(), Reserva.class);
            reserva.setId(id);
            reservaRepo.atualizar(reserva);
            return gson.toJson(reserva);
        });

        patch("/reservas/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Reserva dadosNovos = gson.fromJson(req.body(), Reserva.class);
            reservaRepo.atualizarParcialmente(id, dadosNovos);
            return gson.toJson(reservaRepo.buscarPorId(id));
        });

        delete("/reservas/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            reservasRepo.remover(id);
            res.status(204);
            return "";
        });

        post("/pagamentos", (req, res) -> {
            Pagamento pagamento = gson.fromJson(req.body(), Pagamento.class);
            pagamentoRepo.salvar(pagamento);
            res.status(201);
            return gson.toJson(pagamento);
        })

        get("/pagamentos", (req, res) -> {
            gson.toJson(pagamentoRepo.listar())
        });

        get("/pagamentos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Pagamento pagamento = pagamentoRepo.buscarPorId(id);
            if (pagamento == null) {
                res.status(404);
                return "{\"erro\": \"Pagamento não encontrado\"}";
            }
            return gson.toJson(pagamento);
        });

        put("/pagamentos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Pagamento pagamento = gson.fromJson(req.body(), Pagamento.class);
            pagamento.setId(id);
            pagamentoRepo.atualizar(pagamento);
            return gson.toJson(pagamento);
        });

        patch("/pagamentos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            Pagamento dadosNovos = gson.fromJson(req.body(), Pagamento.class);
            pagamentoRepo.atualizarParcialmente(id, dadosNovos);
            return gson.toJson(pagamentoRepo.buscarPorId(id));
        });

        delete("/pagamentos/:id", (req, res) -> {
            Long id = Long.parseLong(req.params(":id"));
            pagamentosRepo.remover(id);
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
