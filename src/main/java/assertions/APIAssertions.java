package assertions;

import io.restassured.response.Response;
import org.testng.Assert;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class APIAssertions {
	
	//Validate status code
	public static void verifyStatusCode(Response response, int expectedStatusCode) {
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status Code Mismatch!");
	}
	
	//Validate Response Time
	public static void verifyResponseTime(Response response, long maxTime) {
		Assert.assertTrue(response.getTime() < maxTime);
	}
	
	//Validate Field Value (String)
	public static void verifyField(Response response, String jsonPath, String expectedValue) {
		String actualValue = response.jsonPath().getString(jsonPath);
		Assert.assertEquals(actualValue, expectedValue, "Field value mismatch for: "+jsonPath);
	}
	
	//Validate field value (int)
	public static void verifyField(Response response, String jsonPath, int expectedValue) {
		int actualValue = response.jsonPath().getInt(jsonPath);
		Assert.assertEquals(actualValue, expectedValue, "Field value mismatch for: "+jsonPath);
	}
	
	//Validate field not null
	public static void verifyNotNull(Response response, String jsonPath) {
		Object value = response.jsonPath().get(jsonPath);
		Assert.assertNotNull(value, "Value is null for: "+jsonPath);
	}
	
	//Validate Response contains key
	public static void verifyKeyPresent(Response response, String key) {
		Assert.assertTrue(response.asString().contains(key), "Key not found in response: "+key);
	}
	
	//Schema validation
	public static void validateSchema(Response response, String schemaFileName) {
		response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaFileName));
	}
	
	public static void assertTrue(boolean condition, String message) {
	    Assert.assertTrue(condition, message);
	}

}
