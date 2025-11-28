📘 Mensal04 – Sistema de Cadastro de Clientes com Login e Foto (Java + MySQL)

Este projeto é um sistema desktop desenvolvido em Java (Swing) com MySQL, contendo:

Tela de Login com BCrypt
Cadastro de usuário
Cadastro de clientes
Busca automática de endereço via API ViaCEP
Lista/edição/remoção de clientes
Upload de foto (salva no banco como Base64)
DAO organizado por pacotes (MVC)


🛠️ Tecnologias Utilizadas

Java 21
Swing (GUI)
MySQL 8
BCrypt (hash de senha)
API ViaCEP
Maven

🧩 Requisitos

MySQL instalado
Driver do MySQL (o Maven baixa automaticamente)
JDK 21 ou superior

🗄️ Configuração do Banco de Dados

Abra o MySQL Workbench ou o terminal e execute:

✔️ 1. Criar o banco
CREATE DATABASE IF NOT EXISTS mensal04;
USE mensal04;

✔️ 2. Criar tabela de endereços
CREATE TABLE IF NOT EXISTS endereco (
id INT AUTO_INCREMENT PRIMARY KEY,
rua VARCHAR(100),
bairro VARCHAR(100),
cidade VARCHAR(100),
cep VARCHAR(20)
);

✔️ 3. Criar tabela de clientes (ATUALIZADA COM FOTO)
CREATE TABLE IF NOT EXISTS cliente (
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
cpf VARCHAR(20),
rg VARCHAR(20),
endereco_id INT,
foto_base64 LONGTEXT,
FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);


📌 A foto é salva no formato Base64, ocupando um LONGTEXT.

✔️ 4. Criar tabela de usuários (login com BCrypt)
CREATE TABLE IF NOT EXISTS usuario (
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL UNIQUE,
senha VARCHAR(255) NOT NULL,
status VARCHAR(10) DEFAULT 'ATIVO'
);

🔐 Login e Segurança

O sistema utiliza BCrypt para armazenar senhas seguras.
Senha digitada → BCrypt.hashpw
Validação no login → BCrypt.checkpw
Nenhuma senha é armazenada em texto puro.

🖼️ Fotos dos Clientes

O sistema permite escolher uma imagem no computador.
O arquivo é convertido para Base64 e armazenado no MySQL na coluna foto_base64.
A imagem não é exibida no sistema, apenas armazenada (exigência do projeto).


📎 Autor

Projeto desenvolvido por Erick L.