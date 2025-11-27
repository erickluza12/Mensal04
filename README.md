📘 Mensal04 – Sistema de Cadastro

Este projeto é um sistema simples de cadastro de clientes utilizando:

Java 21
IntelliJ IDEA
MySQL
Maven
Swing (interface gráfica)

Este README explica exatamente como rodar o projeto em qualquer PC, mesmo que não tenha variáveis de ambiente configuradas.

🚀 1. Requisitos
Para rodar o sistema, você precisa ter instalado:
✔ Java JDK 21 ou superior
✔ IntelliJ IDEA (Community ou Ultimate)
✔ MySQL Server
✔ MySQL Workbench (opcional)

🗄️ 2. Criando o Banco de Dados
Após abrir o MySQL (Terminal ou Workbench), execute:

CREATE DATABASE mensal04;
USE mensal04;

CREATE TABLE endereco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(120),
    bairro VARCHAR(80),
    cidade VARCHAR(80),
    cep VARCHAR(9)
);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    rg VARCHAR(12),
    ip VARCHAR(50),
    endereco_id INT,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

🔑 3. Configuração de Acesso ao Banco (Modo Faculdade)
O arquivo ConnectionFactory já está configurado para funcionar em qualquer computador, usando uma senha fixa:

package mensal04.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/mensal04";
            String user = "root";
            String pass = "1234"; // Senha padrão utilizada na faculdade

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco: " + e.getMessage());
        }
    }
}


📦 4. Rodando o Projeto no IntelliJ
1️⃣ Abra o IntelliJ
2️⃣ Clique em File > Open
3️⃣ Selecione a pasta Mensal04 (o projeto)
4️⃣ Aguarde o Maven baixar as dependências
5️⃣ Execute o arquivo:
src/main/java/mensal04/main/main.java
