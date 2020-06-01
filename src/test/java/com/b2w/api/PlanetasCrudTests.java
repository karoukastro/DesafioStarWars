package com.b2w.api;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.b2w.api.models.Planeta;
import com.b2w.util.RestAPIConfig;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;

@SpringBootTest
public class PlanetasCrudTests extends RestAPIConfig{
	
	@BeforeAll
	static public void before() {
		
		RestAssured.baseURI = PATH_API;
		deletaTodosPlanetas();
	}
	
	/*
	 * Deleta todos os Planetas antes de começar os tests
	 */
	public static void deletaTodosPlanetas() {
		Response response = buscaTodosPlanetasRest();
		
	    List<String> ids = response.path("results.id");
	    for(String id: ids) {
	    	deletarPlanetaPeloIdRest(id);
	    }
	}
	
	public Response inserirPlanetaRest(Planeta planeta) {
		
		//Adicionar Planeta
		Response response = given().urlEncodingEnabled(true)
						 .log().method()
	  			 		 .log().uri()
	  			 		 .log().body()
			  			 .body(planeta)
			  			 .contentType(ContentType.JSON)
			  			 .when()
			  			 .post("planetas")
			  			 .then()
			  			 .statusCode(200)
			  			 .body("nome", Matchers.is(planeta.getNome()))
			  			 .body("clima", Matchers.is(planeta.getClima()))
			  			 .body("terreno", Matchers.is(planeta.getTerreno()))
			  			 .body("id", Matchers.notNullValue())
						 .extract().response();
		
		response.print();
		return response;
	}
	
	
	public static void deletarPlanetaRest(Planeta p) {
	  	
		//Deletar Planeta
		given().urlEncodingEnabled(true)
						    .log().method()
					 		.log().uri()
							.when()
							.delete("planetas/" + p.getId())
							.then()
							.log().all()
							.statusCode(200)
							.body("message", Matchers.is("O Planeta " + p.getNome() + " foi deletado"))
							.extract().response();
	}
	
	public static void deletarPlanetaPeloIdRest(String id) {
		
		given().urlEncodingEnabled(true)
			    .log().method()
		 		.log().uri()
				.when()
				.delete("planetas/" + id)
				.then()
				.log().all()
				.statusCode(200)
				.extract().response();
	}
	
	public static Response buscaTodosPlanetasRest() {
		
		Response response = given().urlEncodingEnabled(true)
						    .log().method()
					 		.log().uri()
							.get("planetas")
							.then().statusCode(200)
							.extract().response();

			response.print();
			return response;
	}
	
		
	@Test
	public void testeInserirPlaneta() {

		Planeta planeta = new Planeta ("InserirPlaneta", "Seco", "Deserto");
		
	    inserirPlanetaRest(planeta);
	}
	
	
	@Test
	public void testeBuscarTodosPlanetas() {
						
		//Deletar todos os Planetas antes de inserir
		deletaTodosPlanetas();
	    
	    //Inserir Planeta 1
		Planeta planeta1 = new Planeta ("BuscarTodosPlanetas1", "Seco", "Deserto");
		Response response = inserirPlanetaRest(planeta1);
		planeta1.setId(response.path("id"));
		
	    //Inserir Planeta 2
		Planeta planeta2 = new Planeta ("BuscarTodosPlanetas2", "Seco", "Deserto");
		response = inserirPlanetaRest(planeta2);
		planeta2.setId(response.path("id"));
		  		
	    //Inserir Planeta 3 - Com aparicoes
		Planeta planeta3 = new Planeta ("Endor", "Temperado", "Florestas");
		response = inserirPlanetaRest(planeta3);
		planeta3.setId(response.path("id"));
		
	    //Consultar o numero de aparições correta do Planeta 3
	    int aparicoesPlaneta3 = SWapiTests.retornaAparicoes(planeta3.getNome());
		
	    //Buscar Todos os Planetas e verificar resposta retornada
		given().urlEncodingEnabled(true)
			    .log().method()
		 		.log().uri()
				.get("planetas")
				.then().statusCode(200)
				.body("count", Matchers.is(3))
	  			.body("results.id", Matchers.is(Lists.newArrayList(planeta1.getId(), planeta2.getId(), planeta3.getId())))
	  			.body("results.nome", Matchers.is(Lists.newArrayList(planeta1.getNome(), planeta2.getNome(), planeta3.getNome())))
	  			.body("results.clima", Matchers.is(Lists.newArrayList(planeta1.getClima(), planeta2.getClima(), planeta3.getClima())))
	  			.body("results.terreno", Matchers.is(Lists.newArrayList(planeta1.getTerreno(), planeta2.getTerreno(), planeta3.getTerreno())))
	  			.body("results.numAparicoes", Matchers.is(Lists.newArrayList(0, 0, aparicoesPlaneta3)))
	  			.extract().response().print();
	}
	
	@Test
	public void testeBuscarPlanetaPorID() {
		
		//Inserir Planeta
		Planeta planeta = new Planeta ("BuscarPlanetaID", "Seco", "Deserto");
		Response response = inserirPlanetaRest(planeta);
		planeta.setId(response.path("id"));	 
		
	    //Buscar Planetas pelo ID e verificar resposta retornada
		given().urlEncodingEnabled(true)
				    .log().method()
			 		.log().uri()
			 		.log().body()
					.when()
					.get("planetas/id/" + planeta.getId())
					.then()
					.log().all()
					.statusCode(200)
		  			.body("nome", Matchers.is(planeta.getNome()))
		  			.body("clima", Matchers.is(planeta.getClima()))
		  			.body("terreno", Matchers.is(planeta.getTerreno()))
		  			.body("id", Matchers.is(planeta.getId()))
		  			.body("numAparicoes", Matchers.is(0))
					.extract().response().print();
	}
	
	@Test
	public void testeBuscarPlanetaPorNome() {
		
		//Inserir Planeta
		Planeta planeta = new Planeta ("BuscarPlanetaPorNome", "Seco", "Deserto");
		Response response = inserirPlanetaRest(planeta);
		planeta.setId(response.path("id"));
	    			 
	    //Buscar Planeta pelo nome e verificar resposta retornada
		given().urlEncodingEnabled(true)
				    .log().method()
			 		.log().uri()
			 		.log().body()					
 					.when()
					.get("planetas/nome/" + planeta.getNome())
					.then()
					.log().all()
					.statusCode(200)
		  			.body("nome", Matchers.is(planeta.getNome()))
		  			.body("clima", Matchers.is(planeta.getClima()))
		  			.body("terreno", Matchers.is(planeta.getTerreno()))
		  			.body("id", Matchers.is(planeta.getId()))
		  			.body("numAparicoes", Matchers.is(0))
					.extract().response().print();
	}
	
	@Test
	public void testeBuscarPlanetaComAparicoes() {
		
		//Inserir Planeta
		Planeta planeta = new Planeta ("Naboo", "Temperado", "Florestas, Montanhas");
		Response response = inserirPlanetaRest(planeta);
		planeta.setId(response.path("id"));	
		
	    //Consultar na SWApi o número de aparições do Planeta
	    int aparicoes= SWapiTests.retornaAparicoes(planeta.getNome());
		 
	    //Buscar Planeta pelo nome e verificar resposta retornada com número de aparições da SWApi
		given().urlEncodingEnabled(true)
			    .log().method()
		 		.log().uri()
				.get("planetas/nome/" + planeta.getNome())
				.then()
				.log().all()
				.statusCode(200)
				.body("nome", Matchers.is(planeta.getNome()))
				.body("clima", Matchers.is(planeta.getClima()))
				.body("terreno", Matchers.is(planeta.getTerreno()))
				.body("id", Matchers.is(planeta.getId()))
				.body("numAparicoes", Matchers.is(aparicoes))
				.extract().response().print();
	}

	@Test
	public void testeModificarPlaneta(){
		
		//Inserir Planeta
		Planeta planeta = new Planeta ("ModificarPlaneta", "Seco", "Deserto");
		Response response = inserirPlanetaRest(planeta);
		planeta.setId(response.path("id"));
		
		//Alterar os dados para modificar Planeta
	  	planeta.setNome("PlanetaModificado");
	  	planeta.setClima("ClimaModificado");
	  	planeta.setTerreno("TerrenoModificado");
	  	
	  	//Modificar Planeta e verificar resposta retornada
		given().urlEncodingEnabled(true)
			    .log().method()
		 		.log().uri()
		 		.log().body()
				.contentType(ContentType.JSON)
				.body(planeta)
				.when()
				.put("planetas/" + planeta.getId())
				.then()
				.log().all()
				.statusCode(200)
				.body("nome", Matchers.is(planeta.getNome()))
				.body("clima", Matchers.is(planeta.getClima()))
				.body("terreno", Matchers.is(planeta.getTerreno()))
				.body("id", Matchers.is(planeta.getId()))
				.extract().response().print();			  
	}
	
	@Test
	public void testeModificarPlanetaComAparicoes(){
		
		//Inserir Planeta
		Planeta planeta = new Planeta ("GeonosisNew", "Modificar", "Modificar");
		Response response = inserirPlanetaRest(planeta);
		planeta.setId(response.path("id"));
		
		//Alterar os dados para modificar Planeta
	  	planeta.setNome("Geonosis");
	  	planeta.setClima("Árido");
	  	planeta.setTerreno("Montanhas");
	  	
	    //Consultar o numero de aparições correta do Planeta 3
	    int aparicoesPlaneta = SWapiTests.retornaAparicoes(planeta.getNome());
	    
	  	//Modificar Planeta e verificar resposta retornada com número de aparições da SWApi
		given().urlEncodingEnabled(true)
			    .log().method()
		 		.log().uri()
		 		.log().body()
				.contentType(ContentType.JSON)
				.body(planeta)
				.when()
				.put("planetas/" + planeta.getId())
				.then()
				.log().all()
				.statusCode(200)
				.body("nome", Matchers.is(planeta.getNome()))
				.body("clima", Matchers.is(planeta.getClima()))
				.body("terreno", Matchers.is(planeta.getTerreno()))
				.body("id", Matchers.is(planeta.getId()))
	  			.body("numAparicoes", Matchers.is(aparicoesPlaneta))
				.extract().response().print();			  
	}
	
	
	@Test
	public void testeDeletarPlaneta() {
		
		//Inserir Planeta
		Planeta planeta = new Planeta ("DeletarPlaneta", "Seco", "Deserto");
		Response response = inserirPlanetaRest(planeta); 
		planeta.setId(response.path("id"));
		  
		//Deletar Planeta e verificar resposta retornada
	  	deletarPlanetaRest(planeta);  
	}
	
	//Fluxos de exceção
	@Test
	public void testeInserirPlanetaSemNome() {
				
		Planeta planeta = new Planeta (null, "Seco", "Deserto");
		
		//Tentar inserir Planeta  e verificar resposta retornada
		given().urlEncodingEnabled(true)
				 .log().method()
			 	 .log().uri()
			 	 .log().body()
	  			 .body(planeta)
	  			 .contentType(ContentType.JSON)
	  			 .when()
	  			 .post("planetas")
	  			 .then()
	  			 .statusCode(400)
	  			 .body("erros", Matchers.is(Lists.newArrayList("Nome não pode ser vazio")))
				 .extract().response().print();
	}
	
	@Test
	public void testeInserirPlanetaSemClima(){
		
		Planeta planeta = new Planeta ("InserirPlanetaSemClima", null, "Deserto");
		
		//Tentar inserir Planeta  e verificar resposta retornada
		given().urlEncodingEnabled(true)
				 .log().method()
			 	 .log().uri()
			 	 .log().body()
	  			 .body(planeta)
	  			 .contentType(ContentType.JSON)
	  			 .when()
	  			 .post("planetas")
	  			 .then()
	  			 .statusCode(400)
	  			 .body("erros", Matchers.is(Lists.newArrayList("Clima não pode ser vazio")))
				 .extract().response().print();
	}
	
	@Test
	public void testeInserirPlanetaSemTerreno() {
				
		Planeta planeta = new Planeta ("InserirPlanetaSemTerreno", "Seco", null);
		
		//Tentar inserir Planeta e verificar resposta retornada
		given().urlEncodingEnabled(true)
				 .log().method()
			 	 .log().uri()
			 	 .log().body()
	  			 .body(planeta)
	  			 .contentType(ContentType.JSON)
	  			 .when()
	  			 .post("planetas")
	  			 .then()
	  			 .statusCode(400)
	  			 .body("erros", Matchers.is(Lists.newArrayList("Terreno não pode ser vazio")))
				 .extract().response().print();
	}
	
	@Test
	public void testeInserirPlanetaComMesmoNome() {
				
		//Inserir Planeta
		Planeta planeta = new Planeta ("InserirComMesmoNome", "Seco", "Deserto");
		inserirPlanetaRest(planeta);

		//Tentar inserir Planeta novamente e verificar resposta retornada
		given().urlEncodingEnabled(true)
				 .log().method()
			 	 .log().uri()
			 	 .log().body()
	  			 .body(planeta)
	  			 .contentType(ContentType.JSON)
	  			 .when()
	  			 .post("planetas")
	  			 .then()
	  			 .statusCode(400)
	  			 .body("message", Matchers.is("Este Planeta já foi cadastrado anteriormente"))
				 .extract().response().print();
	}
	
	@Test
	public void testeModificarPlanetaInexistente() {
		
	Planeta planeta = new Planeta ("ModificarPlaneta", "Seco", "Deserto");

	//Tentar modificar Planeta inexistente e verificar resposta retornada
	given().urlEncodingEnabled(true)
			 .log().method()
		 	 .log().uri()
		 	 .log().body()
  			 .body(planeta)
  			 .contentType(ContentType.JSON)
  			 .when()
  			 .put("planetas/" + "12354567890")
  			 .then()
  			 .statusCode(400)
  			 .body("message", Matchers.is("Planeta não encontrado"))
			 .extract().response().print();
	}
	
	@Test
	public void testeModificarPlanetaUtilizandoNomeJaUtilizado(){
		
		//Inserir Planeta
		Planeta planeta = new Planeta ("ModificarPlanetaNomeUtilizado", "Seco", "Deserto");
		Response response = inserirPlanetaRest(planeta);
		planeta.setId(response.path("id"));
	  	
	  	//Tentar modificar Planeta e verificar resposta retornada
		given().urlEncodingEnabled(true)
			    .log().method()
		 		.log().uri()
		 		.log().body()
				.contentType(ContentType.JSON)
				.body(planeta)
				.when()
				.put("planetas/" + planeta.getId())
				.then()
				.log().all()
				.statusCode(400)
	  			.body("message", Matchers.is("Já existe planeta cadastrado com esse nome"))
				.extract().response().print();			  
	}
	
	@Test
	public void testeDeletarPlanetaInexistente() {
		
		//Tentar deletar Planeta inexistente e verificar resposta retornada
		given().urlEncodingEnabled(true)
			    .log().method()
		 		.log().uri()
				.when()
				.delete("planetas/" + "1234567890")
				.then()
				.log().all()
				.statusCode(400)
				.body("message", Matchers.is("Planeta não encontrado"))
				.extract().response(); 
	}
	
	@Test
	public void testeBuscarPlanetaPorIdInexistente() {
		
		//Tentar buscar Planeta com ID inexistente e verificar resposta retornada
		given().urlEncodingEnabled(true)
				    .log().method()
			 		.log().uri()
			 		.log().body()
					.when()
					.get("planetas/id/" + "1234567890")
					.then()
					.log().all()
					.statusCode(400)
					.body("message", Matchers.is("Planeta não encontrado"))
					.extract().response().print();
	}
	
	@Test
	public void testeBuscarPlanetaPorNomeInexistente() {
		
		//Tentar buscar Planeta por nome inexistente e verificar resposta retornada
		given().urlEncodingEnabled(true)
				    .log().method()
			 		.log().uri()
			 		.log().body()
					.when()
					.get("planetas/nome/" + "Inexistente")
					.then()
					.log().all()
					.statusCode(400)
					.body("message", Matchers.is("Planeta não encontrado"))
					.extract().response().print();
	}
}
