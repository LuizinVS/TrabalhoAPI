# API Zoológico

API REST desenvolvida em **Spring Boot** para gerenciar informações de um zoológico, permitindo CRUD de animais, cuidadores, habitats e envio de e-mails.

---

## Autores

- Luiz Vinícius Marcelo Mariath
- Pedro Henrique Comandolli
- Brena Barradas
- Kaua Reis Rodrigues

## Tecnologias utilizadas

- Java 21
- Spring Boot 3.5.5
- Spring Data JPA
- MySQL
- Maven

## Estrutura do projeto
```
src
├── main
│   ├── java
│   │   └── com.Aula5.ProjetoZoo.ApiZoologico
│   │       ├── controllers
│   │       ├── models
│   │       ├── repositories
│   │       └── services
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com.Aula5.ProjetoZoo.ApiZoologico
```

## Funcionalidades

- CRUD de **Animais**, **Cuidadores**, **Habitat**, etc.
- Envio de e-mail de atualização de cadastro de animais para cuidadores.
- Consultas com filtros (ex.: buscar animais por espécie ou habitat).
- Integração com banco **MySQL**.

---

## Configuração do projeto

### 1. Clone o repositório: 
```bash
git clone https://github.com/LuizinVS/TrabalhoAPI
```

### 2. Configure o banco de dados MySQL em `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ApiZoologico?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

### 3. Rode o projeto:
```bash
mvn spring-boot:run
```

### 4. Endpoints principais
| Método  | Endpoint                         | Descrição                    |
|---------|----------------------------------|------------------------------|
| `GET`   | /animais                         | Retorna todos os animais     |
| `GET`   | /animais/{id}                    | Retorna um animal por ID     |
| `POST`  | /animais                         | Cria um novo animal          |
| `PUT`   | /animais/{id}                    | Atualiza um animal existente |
| `DELETE`| /animais/{id}                    | Remove um animal             |
| `GET`   | /animais/filtro?especie=Espécie  | Filtra animais por espécie   |

### 4.1. Documentação Swagger

Após rodar o projeto (`mvn spring-boot:run`), você pode acessar a documentação interativa da API via navegador no link:

👉 [Swagger UI](http://localhost:8080/swagger-ui/index.html)

Nela, é possível:
- Visualizar todos os endpoints da API.
- Consultar parâmetros e respostas esperadas.

---

## Licença
- Projeto open source. Sinta-se à vontade para contribuir. 💛
