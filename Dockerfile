# Estágio 1: Build - Usa uma imagem do Maven para compilar o código Java em um arquivo .jar
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos do projeto para o contêiner
COPY . .

# Executa o comando do Maven para compilar e empacotar a aplicação, pulando os testes
RUN mvn clean package -DskipTests


# Estágio 2: Run - Usa uma imagem Java leve apenas para executar a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio anterior para este novo contêiner
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta que o Spring Boot usa por padrão
EXPOSE 8080

# Define o comando que será executado quando o contêiner iniciar
ENTRYPOINT ["java","-jar","app.jar"]