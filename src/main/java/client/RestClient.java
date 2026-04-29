package client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class RestClient {
	
	//GET
	public static Response get(RequestSpecification requestSpec) {
		return given()
				.spec(requestSpec)
				.when()
				.get()
				.then()
				.extract()
				.response();
	}
	
	//POST
	public static Response post(RequestSpecification requestSpec) {
		return given()
				.spec(requestSpec)
				.when()
				.post()
				.then()
				.extract()
				.response();
	}
	
	//PUT (with token)
	public static Response put(RequestSpecification requestSpec) {
		return given()
				.spec(requestSpec)
				.when()
				.put()
				.then()
				.extract()
				.response();
	}
	
	//PATCH
	public static Response patch(RequestSpecification requestSpec) {
		return given()
				.spec(requestSpec)
				.when()
				.patch()
				.then()
				.extract()
				.response();
	}
	
	//DELETE
	public static Response delete(RequestSpecification requestSpec) {
		return given()
				.spec(requestSpec)
				.when()
				.delete()
				.then()
				.extract()
				.response();
	}

}
