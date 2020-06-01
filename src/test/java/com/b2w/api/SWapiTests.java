package com.b2w.api;

import static io.restassured.RestAssured.given;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.response.Response;
import com.b2w.util.RestAPIConfig;

@SpringBootTest
public class SWapiTests extends RestAPIConfig  {

	@Test
	public void checkUrlAPI() {

		given()
				.log().method()
			 	.log().uri()
				.when()
				.get(PATH_SWAPI)
				.then()
				.statusCode(200);
	}
	

	public static int retornaAparicoes(String nome) {
	    
	    Response responseSWApi = given()
									.log().method()
								 	.log().uri()
									.queryParam("search", nome)
									.get(PATH_SWAPI + "planets")
									.then().statusCode(200)
									.extract().response();
	    responseSWApi.print();	
	    List<List<String>> films = responseSWApi.path("results.films");
	    return films.get(0).size();
	}
    
	
}
