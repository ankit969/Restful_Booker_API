package tdd;

import org.testng.annotations.Test;

import assertions.APIAssertions;
import base.BaseTest;
import builder.RequestBuilder;
import client.RestClient;
import constants.Endpoints;
import constants.FrameworkConstants;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.BookingDates;
import models.request.BookingRequest;
import models.response.BookingResponse;
import utils.FakerUtils;

public class BookingTest extends BaseTest{
	
	@Test
	public void createBookingTest() {
		
		//Step1: Create Request Body using POJO + Faker
		BookingDates bookingDates = new BookingDates("2026-05-01", "2026-05-05");
		BookingRequest requestBody = new BookingRequest(FakerUtils.getFirstName(), FakerUtils.getLastName(), FakerUtils.getPrice(), true, bookingDates, FakerUtils.getAdditionalNeeds());
		
		//Step2: Build Request
		RequestSpecification requestSpec = new RequestBuilder()
				.setBasePath(Endpoints.CREATE_BOOKING)
				.addHeader("Content-Type", FrameworkConstants.CONTENT_TYPE)
				.addHeader("Accept", FrameworkConstants.ACCEPT)
				.setBody(requestBody)
				.build();
		
		//Step3: Execute API
		Response response = RestClient.post(requestSpec);
		
		//Step4: Deserialize Response
		BookingResponse bookingReponse = response.as(BookingResponse.class);
		
		//Step5: Validation
		APIAssertions.verifyStatusCode(response, 200);
		APIAssertions.verifyNotNull(response, "bookingid");
		
		//Validate response data matches request
		APIAssertions.verifyField(response, "booking.firstname", requestBody.getFirstname());
		APIAssertions.verifyField(response, "booking.lastname", requestBody.getLastname());
		
		//Optional: Response Time validation
		APIAssertions.verifyResponseTime(response, 2000);
		
		//Print for Debug(Optional)
		System.out.println("Booking ID: " + bookingReponse.getBookingid());
	}

}
