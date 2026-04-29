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

public class E2ETest extends BaseTest{
	
	@Test
    public void bookingE2EFlowTest() {

        // ==============================
        // 🔹 STEP 1: CREATE BOOKING
        // ==============================

        BookingDates bookingDates = new BookingDates("2026-05-01", "2026-05-05");

        BookingRequest createRequest = new BookingRequest(
                FakerUtils.getFirstName(),
                FakerUtils.getLastName(),
                FakerUtils.getPrice(),
                true,
                bookingDates,
                FakerUtils.getAdditionalNeeds()
        );

        RequestSpecification createSpec = new RequestBuilder()
                .setBasePath(Endpoints.CREATE_BOOKING)
                .addHeader("Content-Type", FrameworkConstants.CONTENT_TYPE)
                .addHeader("Accept", FrameworkConstants.ACCEPT)
                .setBody(createRequest)
                .build();

        Response createResponse = RestClient.post(createSpec);

        APIAssertions.verifyStatusCode(createResponse, 200);

        BookingResponse bookingResponse = createResponse.as(BookingResponse.class);
        int bookingId = bookingResponse.getBookingid();

        System.out.println("Created Booking ID: " + bookingId);

        // ==============================
        // 🔹 STEP 2: GET BOOKING
        // ==============================

        RequestSpecification getSpec = new RequestBuilder()
                .setBasePath(Endpoints.GET_BOOKING)
                .addPathParam("id", bookingId)
                .build();

        Response getResponse = RestClient.get(getSpec);

        APIAssertions.verifyStatusCode(getResponse, 200);
        APIAssertions.verifyField(getResponse, "firstname", createRequest.getFirstname());

        // ==============================
        // 🔹 STEP 3: UPDATE BOOKING (PUT)
        // ==============================

        BookingDates updatedDates = new BookingDates("2026-06-01", "2026-06-10");

        BookingRequest updateRequest = new BookingRequest(
                "UpdatedName",
                "UpdatedLast",
                2000,
                false,
                updatedDates,
                "Lunch"
        );

        RequestSpecification updateSpec = new RequestBuilder()
                .setBasePath(Endpoints.UPDATE_BOOKING)
                .addPathParam("id", bookingId)
                .addHeader("Content-Type", FrameworkConstants.CONTENT_TYPE)
                .addHeader("Accept", FrameworkConstants.ACCEPT)
                .setAuthToken(getToken())
                .setBody(updateRequest)
                .build();

        Response updateResponse = RestClient.put(updateSpec);

        APIAssertions.verifyStatusCode(updateResponse, 200);
        APIAssertions.verifyField(updateResponse, "firstname", "UpdatedName");

        // ==============================
        // 🔹 STEP 4: DELETE BOOKING
        // ==============================

        RequestSpecification deleteSpec = new RequestBuilder()
                .setBasePath(Endpoints.DELETE_BOOKING)
                .addPathParam("id", bookingId)
                .setAuthToken(getToken())
                .build();

        Response deleteResponse = RestClient.delete(deleteSpec);

        APIAssertions.verifyStatusCode(deleteResponse, 201);

        // ==============================
        // 🔹 STEP 5: VERIFY DELETION
        // ==============================

        Response getAfterDelete = RestClient.get(getSpec);

        APIAssertions.verifyStatusCode(getAfterDelete, 404);

        System.out.println("Booking successfully deleted!");
    }

}
