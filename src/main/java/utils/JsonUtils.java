package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	//Convert Object -> JSON string
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("JSON Serialixation failed");
		}
	}
	
	//Convert JSON -> Object
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("JSON Deserialization failed");
		}
	}

}
