package base;

import org.testng.annotations.BeforeClass;

import config.ConfigReader;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class BaseTest {
	
	protected static String token;
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI =  ConfigReader.get("url");
		generateToken();
	}
	
	private void generateToken() {
		Map<String, String> payload = new HashMap<>();
		payload.put("username", ConfigReader.get("username"));
		payload.put("password", ConfigReader.get("password"));
		
		Response response = given()
				.header("Content-Type", "application/json")
				.body(payload)
				.post("/auth");
		
		if(response.statusCode() != 200) {
			throw new RuntimeException("Token generation failed: "+response.asString());
		}
		
		token = response.jsonPath().getString("token");
		
		if(token == null)
			throw new RuntimeException("Token is null!");
	}
	
	public static String getToken() {
		return token;
	}

}
