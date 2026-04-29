package services;

import builder.RequestBuilder;
import client.RestClient;
import constants.Endpoints;
import constants.FrameworkConstants;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.BookingRequest;

public class BookingService {
	
	//CREATE BOOKING
	public Response createBooking(BookingRequest request) {
		RequestSpecification requestSpec = new RequestBuilder()
				.setBasePath(Endpoints.CREATE_BOOKING)
				.addHeader("Content-Type", FrameworkConstants.CONTENT_TYPE)
				.addHeader("Accept", FrameworkConstants.ACCEPT)
				.setBody(request)
				.build();
		
		return RestClient.post(requestSpec);
	}
	
	//GET BOOKING
	public Response getBooking(int bookingId) {
		RequestSpecification spec = new RequestBuilder()
                .setBasePath(Endpoints.GET_BOOKING)
                .addPathParam("id", bookingId)
                .build();

        return RestClient.get(spec);
	}
	
	//UPDATE BOOKING
	public Response updateBooking(int bookingId, BookingRequest request, String token) {

        RequestSpecification spec = new RequestBuilder()
                .setBasePath(Endpoints.UPDATE_BOOKING)
                .addPathParam("id", bookingId)
                .addHeader("Content-Type", FrameworkConstants.CONTENT_TYPE)
                .addHeader("Accept", FrameworkConstants.ACCEPT)
                .setAuthToken(token)
                .setBody(request)
                .build();

        return RestClient.put(spec);
    }
	
	//DELETE BOOKING
	public Response deleteBooking(int bookingId, String token) {

        RequestSpecification spec = new RequestBuilder()
                .setBasePath(Endpoints.DELETE_BOOKING)
                .addPathParam("id", bookingId)
                .setAuthToken(token)
                .build();

        return RestClient.delete(spec);
    }

}
