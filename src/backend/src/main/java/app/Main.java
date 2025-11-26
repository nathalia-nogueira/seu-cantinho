package app;

import static spark.Spark.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import app.modelo.*;
import app.modelo.enums.*;
import app.repositorio.*;
import app.controlador.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        enableCORS("*", "GET, POST, PUT, PATCH, DELETE, OPTIONS", "*");

        Gson gson = new Gson();
        UsuarioControlador usuarioControlador = new UsuarioControlador();
        EnderecoControlador enderecoControlador = new EnderecoControlador();
        FilialControlador filialControlador = new FilialControlador();
        PagamentoControlador pagamentoControlador = new PagamentoControlador();
        ReservaControlador reservaControlador = new ReservaControlador();
        EspacoControlador espacoControlador = new EspacoControlador();

        usuarioControlador.rotas();
        enderecoControlador.rotas();
        filialControlador.rotas();
        pagamentoControlador.rotas();
        reservaControlador.rotas();
        espacoControlador.rotas();
        
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
