# 📝 Task API

API REST para gerenciamento de tarefas (tasks), construída com Spring Boot e PostgreSQL. Projeto desenvolvido para revisão e aprimoramento de Java, cobrindo desde o CRUD básico até deploy em produção.

🔗 **Deploy:** [https://task-api-z26x.onrender.com/swagger-ui.html](https://task-api-z26x.onrender.com/swagger-ui.html)

> ⚠️ O serviço está hospedado no plano gratuito do Render. A primeira requisição após um período de inatividade pode levar de 30 a 60 segundos (cold start).

---

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 4.0.7**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL**
- **Maven**
- **SpringDoc OpenAPI** (Swagger)
- **JUnit 5 + Mockito** (testes automatizados)
- **Docker**
- **Render** (deploy)

---

## ✨ Funcionalidades

- CRUD completo de tarefas (criar, listar, buscar por id, atualizar, remover)
- Validação de campos obrigatórios
- Tratamento de erros padronizado (404, 400 com mensagens claras)
- Arquitetura em camadas (Controller → Service → Repository)
- Separação entre entidade e contrato da API via DTO + Mapper
- Paginação e filtro por status (`completed`)
- Documentação interativa via Swagger UI
- Testes automatizados de unidade (Service layer)
- Deploy containerizado com Docker

---

## 📁 Estrutura do projeto

```
src/main/java/guilherme/taskapi/
├── controller/     # Endpoints REST
├── dto/            # Objetos de entrada/saída da API
├── exception/      # Tratamento global de erros
├── mapper/         # Conversão entre Entity e DTO
├── model/          # Entidades JPA
├── repository/     # Acesso ao banco de dados
└── service/        # Regras de negócio

src/test/java/guilherme/taskapi/
└── service/        # Testes unitários (JUnit + Mockito)
```

---

## 🔧 Rodando localmente

### Pré-requisitos

- Java 21+
- Maven
- PostgreSQL instalado e rodando

### Passo a passo

1. Clone o repositório:
```bash
git clone https://github.com/guihsteengel/task-api.git
cd task-api
```

2. Crie o banco de dados:
```sql
CREATE DATABASE taskdb;
```

3. Configure a conexão em `src/main/resources/application.properties` (ou use as variáveis de ambiente abaixo):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

4. Rode a aplicação:
```bash
./mvnw spring-boot:run
```

5. Acesse a documentação:
```
http://localhost:8080/swagger-ui.html
```

### Rodando com Docker

```bash
docker build -t task-api .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/taskdb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  task-api
```

---

## 🧪 Rodando os testes

```bash
./mvnw test
```

---

## 📡 Endpoints

| Método | Rota                  | Descrição                                  |
|--------|-----------------------|---------------------------------------------|
| GET    | `/api/tasks`           | Lista tarefas (paginado, filtro por `completed`) |
| GET    | `/api/tasks/{id}`      | Busca uma tarefa por id                     |
| POST   | `/api/tasks`            | Cria uma nova tarefa                        |
| PUT    | `/api/tasks/{id}`      | Atualiza uma tarefa existente               |
| DELETE | `/api/tasks/{id}`      | Remove uma tarefa                           |

### Exemplo de requisição (POST)

```json
{
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de JPA e Hibernate",
  "completed": false
}
```

### Filtros e paginação (GET)

```
GET /api/tasks?page=0&size=10
GET /api/tasks?completed=true
GET /api/tasks?completed=false&page=0&size=5
```

---

## 🗺️ Próximos passos

- [ ] Autenticação com Spring Security + JWT
- [ ] Relacionamento com entidade de usuário
- [ ] CI/CD com GitHub Actions
- [ ] Testes de integração (Controller/Repository)

---

## 👤 Autor

Desenvolvido por Guilherme Stengel como projeto de estudo e revisão de Java/Spring Boot.
