# Sistema de Transferências

API REST desenvolvida em Java com Spring Boot para simular transferências de moedas entre usuários.

O sistema permite cadastrar usuários, realizar autenticação, consultar saldos, fazer transferências e consultar indicadores de auditoria.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (autenticação)
- MySQL 8.4
- Maven
- Docker e Docker Compose

## Funcionalidades

- Cadastro de usuários com criação automática de conta.
- Saldo inicial de 1.000 moedas para cada usuário.
- Autenticação de usuários utilizando JWT.
- Consulta do saldo da conta autenticada.
- Transferência de moedas entre usuários.
- Validação de saldo disponível.
- Impedimento de transferências para a própria conta.
- Prevenção de transferências duplicadas.
- Controle de concorrência nas transferências.
- Operações de transferência realizadas de forma transacional.
- Consulta de indicadores de auditoria.

## Endpoints

| Método | Endpoint          | Descrição                              | Autenticação |
| ------ | ----------------- | -------------------------------------- | ------------ |
| POST   | `/auth/registrar` | Cadastra um novo usuário               | Não          |
| POST   | `/auth/login`     | Realiza o login e retorna o token JWT  | Não          |
| GET    | `/conta`          | Consulta o saldo da conta autenticada  | Sim          |
| POST   | `/transferencias` | Realiza uma transferência              | Sim          |
| GET    | `/auditoria`      | Consulta indicadores gerais do sistema | Não          |

## Configuração do ambiente

Para executar o projeto, é necessário ter:

- Docker instalado e em execução.
- Docker Compose disponível.

### Configuração das variáveis de ambiente

Na pasta principal do projeto, crie um arquivo chamado `.env`.

Adicione as seguintes variáveis:

```env
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta
```

Substitua os valores por credenciais próprias. A chave JWT deve possuir pelo menos 32 bytes.

**Importante:** não publique o arquivo `.env` no GitHub.

### Executando com Docker Compose

Na pasta principal do projeto, execute:

```bash
docker compose up --build -d
```

Esse comando constrói a imagem da API, inicia o banco de dados MySQL e inicia a aplicação Spring Boot.

A API ficará disponível em:

`http://localhost:8080`

O MySQL ficará disponível na porta `3308` do computador.

### Verificando os containers

Para verificar se os serviços estão funcionando:

```bash
docker compose ps
```

Para consultar os logs da API:

```bash
docker compose logs -f api
```

### Parando a aplicação

Para parar e remover os containers sem apagar os dados do banco:

```bash
docker compose down
```

Os dados são armazenados em um volume Docker, permitindo sua persistência após reinicializações.

## Banco de dados

As tabelas são gerenciadas pelo Hibernate/JPA, utilizando MySQL.

O banco de dados utilizado pela aplicação se chama `transferencias`.
