📘 Mensal04 – Sistema de Cadastro de Clientes com Login e Foto

Java (Desktop) + MySQL

Projeto acadêmico desenvolvido em Java (Swing) com integração ao MySQL, focado na aplicação prática de CRUD, segurança de autenticação, integração com API externa e organização em camadas (MVC).

O projeto foi desenvolvido no 3º período do curso de Análise e Desenvolvimento de Sistemas, com o objetivo de consolidar conceitos fundamentais do desenvolvimento back-end e desktop.

🚀 Funcionalidades

Sistema de login com senhas criptografadas (BCrypt)
Cadastro de usuários
Cadastro, edição, listagem e remoção de clientes
Busca automática de endereço via API ViaCEP
Upload de foto do cliente

A imagem é convertida para Base64 e armazenada no banco de dados
Organização do código em camadas utilizando DAO e padrão MVC
Integração completa com banco de dados MySQL

🛠️ Tecnologias Utilizadas

Java 21
Swing (Interface Gráfica Desktop)
MySQL 8
BCrypt (hash de senhas)
API ViaCEP
Maven

🧩 Requisitos

MySQL instalado
JDK 21 ou superior
Driver do MySQL (gerenciado automaticamente pelo Maven)

🔐 Login e Segurança

O sistema utiliza BCrypt para garantir a segurança das senhas:
Senha digitada → BCrypt.hashpw
Validação no login → BCrypt.checkpw
Nenhuma senha é armazenada em texto puro no banco de dados

🖼️ Fotos dos Clientes

O sistema permite selecionar uma imagem do computador.
O arquivo é convertido para Base64 e armazenado no MySQL na coluna foto_base64.
A imagem não é exibida no sistema, apenas armazenada no banco (exigência do projeto).

🧠 Aprendizados

Implementação de CRUD completo em Java
Uso de BCrypt para autenticação segura
Integração com API externa (ViaCEP)
Modelagem e relacionamento de tabelas no MySQL
Organização de código utilizando DAO e MVC
Estruturação de projeto com Maven

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

✔️ 3. Criar tabela de clientes (com foto)
CREATE TABLE IF NOT EXISTS cliente (
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
cpf VARCHAR(20),
rg VARCHAR(20),
endereco_id INT,
foto_base64 LONGTEXT,
FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

📌 A foto é salva no formato Base64, ocupando um campo LONGTEXT.

✔️ 4. Criar tabela de usuários (login com BCrypt)
CREATE TABLE IF NOT EXISTS usuario (
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL UNIQUE,
senha VARCHAR(255) NOT NULL,
status VARCHAR(10) DEFAULT 'ATIVO'
);

📎 Autor

Erick Gabriel Mertz Luza
Estudante de Análise e Desenvolvimento de Sistemas
