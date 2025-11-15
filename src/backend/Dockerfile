FROM maven:3.9.8-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Faz o download das dependências e empacota o projeto
RUN mvn clean package -DskipTests

# Imagem final apenas com o Java
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o .jar gerado pela fase anterior
COPY --from=build /app/target/backend-1.0.jar app.jar

# Executa o backend
CMD ["java", "-jar", "app.jar"]
