package services;

import builder.RequestBuilder;
import client.RestClient;
import config.ConfigReader;
import constants.Endpoints;
import constants.FrameworkConstants;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.AuthRequest;
import models.response.AuthResponse;

public class AuthService {
	
	private static ThreadLocal<String> token = new ThreadLocal<>();

    // 🔹 Generate Token (Called internally)
    private static String generateToken() {

        AuthRequest request = new AuthRequest(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

        RequestSpecification spec = new RequestBuilder()
                .setBasePath(Endpoints.AUTH)
                .addHeader("Content-Type", FrameworkConstants.CONTENT_TYPE)
                .setBody(request)
                .build();

        Response response = RestClient.post(spec);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to generate auth token");
        }

        AuthResponse authResponse = response.as(AuthResponse.class);
        return authResponse.getToken();
    }
    
    public static Response generateInvalidToken() {

        AuthRequest request = new AuthRequest("wrongUser", "wrongPass");

        RequestSpecification spec = new RequestBuilder()
                .setBasePath(Endpoints.AUTH)
                .addHeader("Content-Type", FrameworkConstants.CONTENT_TYPE)
                .setBody(request)
                .build();

        return RestClient.post(spec);
    }

    // 🔹 Public method to get token (with caching)
    public static String getToken() {

        if (token.get() == null) {
            token.set(generateToken());
        }

        return token.get();
    }

    // 🔹 Force refresh token (if needed)
    public static String refreshToken() {
    	token.set(generateToken());
        return token.get();
    }

}
