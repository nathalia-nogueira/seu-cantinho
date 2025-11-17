package app;

import static spark.Spark.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import app.modelo.*;
import app.modelo.enums.*;

public class Main {
    public static void main(String[] args) {
        port(8080);

        String url = "jdbc:postgresql://" + System.getenv("DB_HOST") + ":5432/" + System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASS");

        System.out.println("USER: " + user);
        enableCORS("*", "GET,POST,OPTIONS", "*");

        // --- Teste de conexão com o banco ---
        get("/ping", (req, res) -> {
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                return "Conexão OK com Postgres!";
            } catch (Exception e) {
                res.status(500);
                return "Erro ao conectar: " + e.getMessage();
            }
        });

        // --- Teste das classes modelo ---
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

            return ("Instâncias criadas com sucesso: " + reserva.toString() + ", " + cliente.getNome() + ", " + espaco.getNome());
        });

        System.out.println("Servidor Spark rodando na porta 8080...");
        awaitInitialization();
    }

    private static void enableCORS(final String origin, final String methods, final String headers) {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Allow-Methods", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}