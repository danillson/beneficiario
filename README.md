# ekan-api

API em Java e Spring Framework.

**Body:**

<code>
{
  "id": 1,
  "nome": "JOAO",
  "telefone": "011 4444-5555",
  "dataDeNascimento": "2010-11-20T00:00:00.500Z",
  "documentos: " {
	"id": 1,
	"TipoDocumento": "CPF",
	"Descrição": "Cadastro de Pessoa Física"
  }
}
</code>

`PUT/api-ekan/beneficiario/{id}`: 
Atualizar 

**Body:**

<code>
{
  "id": 1,
  "nome": "JOAO",
  "telefone": "011 6666-7777",
  "dataDeNascimento": "2010-11-20T00:00:00.500Z",
  "endDate": "2019-09-21T21:05:06.500Z",
}
</code>

Deve ser enviado o objeto que será modificado. O retorno deve ser o próprio objeto modificado.

<code>
{
  "id": 1,
  "orderNumber": "220788",
  "amount": "50.50",
  "startDate": "2019-09-11T09:59:51.312Z",
  "endDate": "2019-09-21T21:05:06.500Z",
  "type": "RETURN"
}
</code>

A resposta deve conter os códigos a seguir:

* 200: em caso de sucesso.
* 400: caso o JSON seja inválido.
* 404: caso tentem atualizar um registro que não existe.
* 422: se qualquer um dos campos não for parseável (JSON mal formatado).


### Execução

```
mvn spring-boot:run -Dspring.profiles.active=dev
```
Disponível no endereço [http://localhost:8080/](http://localhost:8080/)
