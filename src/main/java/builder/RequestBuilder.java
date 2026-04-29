package builder;

import java.util.HashMap;
import java.util.Map;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestBuilder {
	
	private RequestSpecBuilder specBuilder;
	private Map<String, Object> pathParams;
	
	public RequestBuilder() {
		specBuilder = new RequestSpecBuilder();
		pathParams = new HashMap<>();
	}
	
	//Set Base Path (endpoint)
	public RequestBuilder setBasePath(String basePath) {
		specBuilder.setBasePath(basePath);
		return this;
	}
	
	//Add Header
	public RequestBuilder addHeader(String key, String value) {
		specBuilder.addHeader(key, value);
		return this;
	}
	
	//Add Header (bulk)
	public RequestBuilder addHeaders(Map<String, String> headers) {
		specBuilder.addHeaders(headers);
		return this;
	}
	
	//Add Query Params
	public RequestBuilder addQueryParam(String key, Object value) {
		specBuilder.addQueryParam(key, value);
		return this;
	}
	
	//Add Path Params
	public RequestBuilder addPathParam(String kye, Object value) {
		pathParams.put(kye, value);
		return this;
	}
	
	//Set Body
	public RequestBuilder setBody(Object body) {
		specBuilder.setBody(body);
		return this;
	}
	
	//Set Auth Token (Cookie-based)
	public RequestBuilder setAuthToken(String token) {
		specBuilder.addHeader("Cookie", "token=" + token);
		return this;
	}
	
	//Build RequestSpecification
	public RequestSpecification build() {
		RequestSpecification requestSpec = specBuilder.build();
		
		if(!pathParams.isEmpty()) {
			requestSpec.pathParams(pathParams);
		}
		
		return requestSpec;
	}

}
