# Sistema de Transferências

API REST desenvolvida em Java com Spring Boot para simular transferências de moedas entre usuários.
O sistema permite cadastrar usuários, realizar autenticação, consultar saldos, fazer transferências e consultar o histórico de operações.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (autenticação)
- MySQL 8.4
- Maven
- Docker

## Funcionalidades

- Cadastro de usuários com criação automática de conta.
- Autenticação de usuários utilizando JWT.
- Consulta do saldo da conta autenticada.
- Transferência de moedas entre usuários.
- Validação de saldo disponível.
- Impedimento de transferências para a própria conta.
- Prevenção de transferências duplicadas.
- Controle de concorrência nas transferências.
- Consulta do histórico de transferências.

## Endpoints

| Método | Endpoint          | Descrição                              | Autenticação |
| ------ | ----------------- | -------------------------------------- | ------------ |
| POST   | `/auth/registrar` | Cadastra um novo usuário               | Não          |
| POST   | `/auth/login`     | Realiza o login e retorna o token JWT  | Não          |
| GET    | `/conta`          | Consulta o saldo da conta autenticada  | Sim          |
| POST   | `/transferencias` | Realiza uma transferência              | Sim          |
| GET    | `/auditoria`      | Consulta o histórico de transferências | Sim          |

## Configuração do ambiente

Antes de executar a aplicação, é necessário ter:

- Java 21 instalado.
- Docker instalado e em execução.

### Executando o MySQL com Docker

No terminal, execute:

```bash
docker run --name mysql-transferencias -e MYSQL_ROOT_PASSWORD=sua_senha -e MYSQL_DATABASE=transferencias -p 3307:3306 -d mysql:8.4
```

Substitua `sua_senha` por uma senha de sua preferência.
O comando cria um contêiner MySQL e um banco de dados chamado `transferencias`, disponibilizado na porta `3307` do computador.

### Variáveis de ambiente

A aplicação utiliza as seguintes variáveis de ambiente:

| Variável      | Descrição                                          |
| ------------- | -------------------------------------------------- |
| `DB_URL`      | URL de conexão com o MySQL                         |
| `DB_USER`     | Usuário do banco de dados                          |
| `DB_PASSWORD` | Senha do banco de dados                            |
| `JWT_SECRET`  | Chave secreta utilizada para assinar os tokens JWT |

Configure as variáveis com os seguintes valores:

- `DB_URL`: `jdbc:mysql://localhost:3307/transferencias`
- `DB_USER`: `root`
- `DB_PASSWORD`: a mesma senha definida no comando Docker.
- `JWT_SECRET`: uma chave secreta própria com pelo menos 32 bytes.

## Executando a aplicação

Após iniciar o MySQL e configurar as variáveis de ambiente, abra o terminal na pasta principal do projeto e execute:

No Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

## Banco de dados

As tabelas são gerenciadas pelo Hibernate/JPA
