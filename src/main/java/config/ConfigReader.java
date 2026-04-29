package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties prop = new Properties();
	
	static {
		try(InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")){
			if(inputStream == null)
				throw new RuntimeException("config.properties file not found");
			
			prop.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config.properties file");
		}
	}
	
	public static String get(String key) {
		String value = prop.getProperty(key);
		
		if(value == null || value.trim().isEmpty())
			throw new RuntimeException(key+ " not found or empty in config.properties file");
		
		return value;
	}

}
