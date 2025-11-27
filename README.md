📘 Sistema Mensal 04 — Java + MySQL + Swing

Sistema desktop desenvolvido em Java (Swing) com persistência em MySQL, utilizando DAO, MVC, hash seguro com BCrypt, e busca automática de endereço via API ViaCEP.
Projeto criado para fins acadêmicos e estruturado para ser fácil de instalar, rodar e apresentar.

🚀 Funcionalidades
👤 Autenticação (Login com BCrypt)

Tela de login modal (bloqueia o sistema até o usuário autenticar).
Cadastro de novos usuários.
Senhas armazenadas com hash BCrypt (segurança real).

🧾 Clientes

Cadastro de clientes.
Busca de CEP automática integrada ao ViaCEP.
Edição completa de clientes.
Remoção de clientes.
Listagem com filtro por nome e tabela não editável.

🏗️ Arquitetura

MVC organizado.
DAOs independentes.
Conexão via ConnectionFactory.
Telas feitas em Swing com navegação estruturada.

🛠️ Como Instalar
1️⃣ Clone o repositório
git clone https://github.com/erickluza12/Mensal04.git
cd Mensal04

🗄️ Configuração do MySQL
2️⃣ Crie o banco
CREATE DATABASE mensal04;
USE mensal04;

3️⃣ Crie as tabelas
🧍‍♂️ Tabela cliente
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(15),
    rg VARCHAR(20),
    endereco_id INT,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

🏠 Tabela endereco
CREATE TABLE endereco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(100),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    cep VARCHAR(10)
);

🔐 Tabela usuario
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) UNIQUE,
    senha VARCHAR(255),
    status VARCHAR(10)
);

⚙️ Configure o ConnectionFactory

Edite o arquivo:
src/main/java/mensal04/dao/ConnectionFactory.java

E coloque seu usuário/senha do MySQL:

private static final String URL = "jdbc:mysql://localhost:3306/mensal04";
private static final String USER = "root";        // seu usuário
private static final String PASS = "sua_senha";   // sua senha

📦 Dependências (Maven)

O projeto já inclui no pom.xml:
MySQL Connector
OkHttp + JSON (ViaCEP)
BCrypt 0.4

Nada precisa ser instalado manualmente.

▶️ Como Rodar o Sistema

Basta executar:

Main.java


Localização:

src/main/java/mensal04/main/Main.java

Este projeto inclui:

✔ Swing bem organizado
✔ Fluxo completo de CRUD
✔ Login com BCrypt
✔ Consumo de API real (ViaCEP)
✔ Padrão MVC
✔ DAO limpo
✔ Banco relacional (MySQL)

📌 Autor

Erick L.
Projeto desenvolvido para fins acadêmicos.
