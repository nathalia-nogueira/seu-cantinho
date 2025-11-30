package app.config;

import spark.Spark;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SwaggerConfig {
    
    public static void setupSwagger() {
        Spark.get("/openapi.yaml", (req, res) -> {
            res.type("application/yaml");
            String content = null;
            
            // 1. Tenta carregar do classpath (funciona no JAR)
            try (InputStream inputStream = SwaggerConfig.class.getClassLoader()
                    .getResourceAsStream("openapi.yaml")) {
                if (inputStream != null) {
                    content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("✅ openapi.yaml carregado do classpath");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro ao carregar do classpath: " + e.getMessage());
            }
            
            // 2. Se não encontrou no classpath, tenta do filesystem
            if (content == null) {
                try {
                    content = Files.readString(Paths.get("openapi.yaml"), StandardCharsets.UTF_8);
                    System.out.println("✅ openapi.yaml carregado do filesystem");
                } catch (Exception e) {
                    System.out.println("❌ Erro ao carregar do filesystem: " + e.getMessage());
                }
            }
            
            if (content != null) {
                return content;
            } else {
                res.status(404);
                return "OpenAPI specification not found";
            }
        });
        
        // Swagger UI
        Spark.get("/docs", (req, res) -> {
            res.type("text/html");
            return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>API Seu Canyinho - Swagger UI</title>
                    <link rel="stylesheet" type="text/css" 
                          href="https://unpkg.com/swagger-ui-dist@4.18.2/swagger-ui.css">
                </head>
                <body>
                    <div id="swagger-ui"></div>
                    <script src="https://unpkg.com/swagger-ui-dist@4.18.2/swagger-ui-bundle.js"></script>
                    <script>
                        SwaggerUIBundle({
                            url: '/openapi.yaml',
                            dom_id: '#swagger-ui',
                            presets: [
                                SwaggerUIBundle.presets.apis,
                                SwaggerUIBundle.presets.standalone
                            ],
                            layout: "BaseLayout"
                        });
                    </script>
                </body>
                </html>
                """;
        });
        
        Spark.get("/", (req, res) -> {
            res.redirect("/docs");
            return null;
        });
    }
}