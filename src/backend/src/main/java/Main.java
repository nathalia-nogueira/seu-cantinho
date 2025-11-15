import static spark.Spark.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        port(8080);

        String url = "jdbc:postgresql://" + System.getenv("DB_HOST") + ":5432/" + System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASS");

        // --- Habilita CORS ---
        enableCORS("*", "GET,POST,OPTIONS", "*");

        // --- Rota principal ---
        get("/ping", (req, res) -> {
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                return "Conexão OK com Postgres!";
            } catch (Exception e) {
                res.status(500);
                return "Erro ao conectar: " + e.getMessage();
            }
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
