<h1> B2W for Women - Desafio API Star Wars </h1>

<div align="center">
  <img src="img/starwarsgrils.png"/> 
</div>

## Índice

<ol>
  <li><a href="#Sobre">Sobre a API</a></li>
  <li><a href="#Tecnologias">Tecnologias utilizadas</a></li>
  <li><a href="#Como">Como configurar a máquina para utilização da API</a></li>
  <li><a href="#Funcionalidades">Funcionalidades</a></li>
  <li><a href="#Rotas">Rotas</a></li>
  <li><a href="#Testes">Testes</a></li>
</ol>

### <a name="Sobre">1. Sobre a API</a>

Esta é uma API REST que armazena dados dos planetas (nome, clima e terreno). A API do Star Wars é consultada para retornar a quantidade de aparições em dos planetas nos filmes da franquia.

### <a name="Tecnologias">2. Tecnologias utilizadas</a>
	
- Java 8
- Spring Boot
- Maven
- Mongo DB
- Testes com Rest Assured / Junit / Postman

### <a name="Como">3. Como configurar a máquina para utilização da API</a>

O primeiro passo é realizar a instalação do Java SE JDK 8, após isso realizar a instalação do STS 4 (Spring Tool Suite 4) e o MongoDB (deixar o MongoDB rodando como serviço). 

### <a name="Funcionalidades">4. Funcionalidades</a>

A API pode ser acessada pelo caminho http://localhost:8080  

### <a name="Rotas">5. Rotas</a>

| Tipo de Requisição | Caminho           | Descrição |
| ---        | ---                       | --- |
| GET        | /api/planetas/            | Busca todos os planetas
| GET        | /api/planetas/nome/{nome} | Busca planeta pelo nome
| GET        | /api/planetas/id/{id}     | Busca planeta pelo ID
| POST       | /api/planetas             | Insere um planeta
| PUT        | /api/planetas/{id}        | Modifica um planeta pelo ID
| DELETE     | /api/planetas/{id}        | Deleta um planeta pelo ID


As requisições POST e PUT necessitam enviar um JSON com as informações. 


#### Inserindo um planeta

Requisição: POST<br />
Caminho: http://localhost:8080/api/planetas<br />
Body:<br />
```
{
    "nome": "Geonosis",
    "clima": "Árido",
    "terreno": "Montanhas"
}
```
Exemplo de resposta: 
```
{
    "id": "5ed3f4274c04247733a82684",
    "nome": "Geonosis",
    "clima": "Árido",
    "terreno": "Montanhas",
    "numAparicoes": 1
}
```
Todos os valores são obrigatórios. Caso o JSON esteja com algum item faltando, a API retornará erro 400. 

#### Buscar todos os planetas

Requisição: GET<br />
Caminho: http://localhost:8080/api/planetas<br />
Body: Não necessário<br />
Exemplo de resposta: <br />
```
{
    "count": 3,
    "results": [
        {
            "id": "5ed3df914c04247733a8267e",
            "nome": "Endor",
            "clima": "Temperado",
            "terreno": "Florestas",
            "numAparicoes": 1
        },
        {
            "id": "5ed3df974c04247733a82683",
            "nome": "Naboo",
            "clima": "Temperado",
            "terreno": "Florestas, Montanhas",
            "numAparicoes": 4
        },
        {
            "id": "5ed3f4274c04247733a82684",
            "nome": "Geonosis",
            "clima": "Árido",
            "terreno": "Montanhas",
            "numAparicoes": 1
        }
    ]
}
```

#### Buscar planetas pelo nome

Requisição: GET<br />
Caminho:http://localhost:8080/api/planetas/nome/{nome}<br />
Body: Não necessário<br />
Exemplo de resposta:<br />
```
{
    "id": "5ed3f4274c04247733a82684",
    "nome": "Geonosis",
    "clima": "Árido",
    "terreno": "Montanhas",
    "numAparicoes": 1
}
``` 

#### Buscar planetas pelo ID

Requisição: GET<br />
Caminho:http://localhost:8080/api/planetas/id/{id}<br />
Body: Não necessário<br />
Exemplo de resposta:<br />
```
{
    "id": "5ed3f4274c04247733a82684",
    "nome": "Geonosis",
    "clima": "Árido",
    "terreno": "Montanhas",
    "numAparicoes": 1
}
```
 
#### Modificar um planeta pelo ID

Requisição: PUT<br />
Caminho:http://localhost:8080/api/planetas/{id}<br />
Body: <br />
```    
{
    "nome": "Geonosis",
    "clima": "Árido",
    "terreno": "Montanhas"
}
```
Exemplo de resposta:<br />
```
{
    "id": "5ed3df914c04247733a8267e",
    "nome": "Geonosis",
    "clima": "Árido",
    "terreno": "Montanhas",
    "numAparicoes": 1
}
```

Caso já exista um planeta com o mesmo nome ou o ID não exista, a API retornará erro 400. 

#### Deletar um planeta pelo ID

Requisição: DELETE<br />
Caminho:http://localhost:8080/api/planetas/{id}<br />
Body: Não necessário<br />
Exemplo de resposta:<br />

```
{
    "message": "O Planeta Naboo foi deletado"
}
```

### <a name="Testes">6. Testes</a>

Os testes estão no caminho: src/test/java/com/b2w/api/PlanetasCrudTests.java
