package tdd;

import org.testng.annotations.Test;

import assertions.APIAssertions;
import io.restassured.response.Response;
import services.AuthService;

public class AuthTest {
	
	// 🔹 TC_01: Generate Token Successfully
    @Test
    public void generateToken_validCredentials() {

        String token = AuthService.getToken();

        APIAssertions.assertTrue(token != null && !token.isEmpty(),
                "Token should not be null or empty");
    }
    
 // 🔹 TC_02: Invalid Credentials
    @Test
    public void generateToken_invalidCredentials() {

        // You’ll need method in AuthService (we’ll add below)
        Response response = AuthService.generateInvalidToken();

        APIAssertions.verifyStatusCode(response, 200);  // API returns 200 even on failure
        APIAssertions.verifyField(response, "reason", "Bad credentials");
    }

}
