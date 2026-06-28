# Backend-Agenda

## Sobre o projeto

O Backend da Agenda de Contatos é uma API REST desenvolvida em **Java** utilizando **Spring Boot**, responsável pelo gerenciamento dos usuários e contatos da aplicação.

A API permite realizar autenticação de usuários, cadastro, edição, consulta e desativação lógica de contatos, mantendo os dados persistidos em banco de dados MySQL.

O projeto foi desenvolvido seguindo a arquitetura em camadas (Controller, Service e Repository), utilizando boas práticas de desenvolvimento e comunicação entre frontend e backend através de requisições HTTP.

---

## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Validation
* MySQL
* Maven

---

## Funcionalidades

* Login de usuário
* Cadastro de contatos
* Listagem de contatos ativos
* Pesquisa de contatos
* Edição de contatos
* Desativação lógica de contatos
* Relacionamento entre usuário e contatos

---

## Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/rafa8641/Backend-Agenda.git
```

---

### 2. Abra o projeto

Abra o projeto em sua IDE de preferência (IntelliJ IDEA, Eclipse ou VS Code).

---

### 3. Configure o banco de dados

Crie um banco de dados MySQL.

Exemplo:

```sql
CREATE DATABASE agenda;
```

Depois configure o arquivo:

```
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/agenda
spring.datasource.username=root
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
```

---

### 4. Instale as dependências

```bash
mvn clean install
```

---

### 5. Execute a aplicação

Pela IDE execute:

```
AgendaApplication.java
```

ou via terminal:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```
http://localhost:8080
```

---

## Principais endpoints

### Login

```
POST /usuarios/login
```

### Contatos

```
GET    /contatos/usuario/{id}/ativos
POST   /contatos
PUT    /contatos/{id}
PATCH  /contatos/{id}/desativar
```

---

## Autor

Desenvolvido por **Rafaela Margutte**.
