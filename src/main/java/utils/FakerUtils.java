package utils;

import com.github.javafaker.Faker;

public class FakerUtils {
	
	private static final Faker faker = new Faker();
	
	public static String getFirstName() {
		return faker.name().firstName();
	}
	
	public static String getLastName() {
		return faker.name().lastName();
	}
	
	public static int getPrice() {
		return faker.number().numberBetween(100, 5000);
	}
	
	public static String getAdditionalNeeds() {
		return faker.food().dish();
	}

}
