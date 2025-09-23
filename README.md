# 🦁 Zoológico API REST

## 📌 Descrição
API REST para gerenciar um zoológico, permitindo CRUD completo em **Animais, Habitats, Cuidadores, Veterinários e Alimentações**. Possui endpoints de filtragem para consultas específicas e respeita regras de negócio como capacidade de habitats e associação de cuidadores aos animais.

---

## 👥 Participantes
- Kauã Reis Rodrigues  
- Luis  
- Pedro  
- Brena  

---

## ⚙️ Tecnologias Utilizadas
- Java 21  
- Spring Boot 3  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Postman (para testes)  
- Maven  

---

## 🏛️ Entidades

### Animal
- **id**: Long  
- **nome**: String  
- **espécie**: String  
- **idade**: Integer  
- **habitat**: Habitat  
- **cuidador**: Cuidador  

### Habitat
- **id**: Long  
- **nome**: String  
- **tipo**: String (terrestre, aquático, aéreo)  
- **capacidadeMaxima**: Integer  

### Cuidador
- **id**: Long  
- **nome**: String  
- **especialidade**: String  
- **turno**: Enum (MANHA, TARDE, NOITE)  

### Veterinário
- **id**: Long  
- **nome**: String  
- **CRMV**: String  
- **especialidade**: String  

### Alimentação
- **id**: Long  
- **tipoComida**: String  
- **quantidadeDiaria**: Double  
- **animal**: Animal  

---

## 🔍 Funcionalidades

### CRUD
- **Criar**: `POST /entidade`  
- **Listar todos**: `GET /entidade`  
- **Atualizar**: `PUT /entidade/{id}`  
- **Deletar**: `DELETE /entidade/{id}`  

### Filtros
- **Animal**
  - `GET /animais?especie=Leão`  
  - `GET /animais?idadeMin=5&idadeMax=10`  
  - `GET /animais?nomeParcial=Leo`  
- **Habitat**
  - `GET /habitats?tipo=terrestre`  
- **Cuidador**
  - `GET /cuidadores?especialidade=Répteis`  
  - `GET /cuidadores?turno=MANHA`  
- **Veterinário**
  - `GET /veterinarios?especialidade=Felinos`  
- **Alimentação**
  - `GET /alimentacoes?tipoComida=Frutas`  
  - `GET /alimentacoes?animalId=3`  

---

## 📊 Regras de Negócio
1. Um habitat não pode ultrapassar sua capacidade máxima de animais.  
2. Cada animal deve ter pelo menos um cuidador associado.  

---

## 🚀 Documentação da API REST – Zoológico

*Base URL:* http://localhost:8080 (ajuste conforme a porta da sua aplicação)

A API permite gerenciar as seguintes entidades: *Cuidador, Habitat, Veterinário e Alimentação*.  
Cada entidade possui endpoints de *CRUD (Create, Read, Update, Delete)*. Alguns endpoints possuem filtros.

---

## 🧑‍💼 Cuidador

*Base URL:* /cuidadores

### Endpoints:

*GET /cuidadores*  
Lista todos os cuidadores. Permite filtros opcionais via query params:  
- especialidade → filtra pelo tipo de especialidade.  
- turno → filtra pelo turno (MANHA, TARDE, NOITE).  

*Exemplos:*
```http
GET /cuidadores
GET /cuidadores?especialidade=Mamíferos
GET /cuidadores?turno=MANHA
GET /cuidadores?especialidade=Mamíferos&turno=MANHA

GET /cuidadores/{id}
Retorna um cuidador pelo ID.

POST /cuidadores
Cria um novo cuidador. JSON de exemplo:

{
  "nome": "Carlos Silva",
  "especialidade": "Mamíferos",
  "turno": "MANHA"
}

PUT /cuidadores/{id}
Atualiza um cuidador existente. Mesma estrutura do POST.

DELETE /cuidadores/{id}
Remove um cuidador pelo ID.
```

---

🌿 Habitat

Base URL: /habitats

Endpoints:
```http
GET /habitats
```
Lista todos os habitats. Filtros opcionais:

tipo → filtra habitats pelo tipo (terrestre, aquático, aéreo).

nome → busca habitats pelo nome (contendo a string).


Exemplos:
```http
GET /habitats
GET /habitats?tipo=terrestre
GET /habitats?nome=Savana

GET /habitats/{id}
Busca um habitat pelo ID.

POST /habitats
Cria um habitat. JSON de exemplo:

{
  "nome": "Savana",
  "tipo": "terrestre",
  "capacidadeMaxima": 15
}

PUT /habitats/{id}
Atualiza um habitat existente.

DELETE /habitats/{id}
Remove um habitat pelo ID.
```

---

🩺 Veterinário

Base URL: /veterinario

Endpoints:
```http
GET /veterinario
Lista todos os veterinários.

GET /veterinario/{id}
Busca um veterinário pelo ID.

GET /veterinario/especialidade/{especialidade}
Busca todos os veterinários que possuem a especialidade informada.
```
Exemplo:
```http
GET /veterinario/especialidade/Felinos

POST /veterinario
Cria um veterinário. JSON de exemplo:

{
  "nome": "Ana Souza",
  "idade": 35,
  "CRMV": 12345,
  "especialidade": "Felinos"
}

PUT /veterinario/{id}
Atualiza um veterinário existente. Exemplo de JSON:

{
  "nome": "Ana Souza",
  "idade": 36,
  "CRMV": 12345,
  "especialidade": "Felinos"
}

DELETE /veterinario/{id}
Remove um veterinário pelo ID.
```

---

🍽 Alimentação

Base URL: /alimentacao

Endpoints:
```http
GET /alimentacao
Lista todas as alimentações.
(Filtros comentados no código: tipoComida e animalId)

GET /alimentacao/{id}
Busca uma alimentação pelo ID. (comentado no controller)

POST /alimentacao
Cria uma alimentação. JSON de exemplo:

{
  "tipoComida": "CARNE",
  "quantidadeDiaria": 5.0
}

PUT /alimentacao/{id}
Atualiza uma alimentação existente. Mesma estrutura do POST.

DELETE /alimentacao/{id}
Remove uma alimentação pelo ID.
```
---
