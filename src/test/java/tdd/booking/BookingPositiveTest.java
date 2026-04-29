package tdd.booking;

import org.testng.annotations.Test;

import assertions.APIAssertions;
import base.BaseTest;
import io.restassured.response.Response;
import models.request.BookingDates;
import models.request.BookingRequest;
import services.AuthService;
import services.BookingService;
import utils.FakerUtils;

public class BookingPositiveTest extends BaseTest{
	
	BookingService bookingService = new BookingService();
	
	// 🔹 TC_01: Create Booking with Valid Data
    @Test
    public void createBooking_validPayload() {

        BookingRequest request = new BookingRequest(
                FakerUtils.getFirstName(),
                FakerUtils.getLastName(),
                FakerUtils.getPrice(),
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                FakerUtils.getAdditionalNeeds()
        );

        Response response = bookingService.createBooking(request);

        APIAssertions.verifyStatusCode(response, 200);
        APIAssertions.verifyNotNull(response, "bookingid");
    }

    // 🔹 TC_02: Get Booking with Valid ID
    @Test
    public void getBooking_validId() {

        BookingRequest request = new BookingRequest(
                FakerUtils.getFirstName(),
                FakerUtils.getLastName(),
                FakerUtils.getPrice(),
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                FakerUtils.getAdditionalNeeds()
        );

        Response createResponse = bookingService.createBooking(request);
        int bookingId = createResponse.jsonPath().getInt("bookingid");

        Response getResponse = bookingService.getBooking(bookingId);

        APIAssertions.verifyStatusCode(getResponse, 200);
        APIAssertions.verifyField(getResponse, "firstname", request.getFirstname());
    }

    // 🔹 TC_03: Update Booking with Valid Data
    @Test
    public void updateBooking_validData() {

        // Create booking
        BookingRequest createRequest = new BookingRequest(
                "John",
                "Doe",
                1000,
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                "Breakfast"
        );

        Response createResponse = bookingService.createBooking(createRequest);
        int bookingId = createResponse.jsonPath().getInt("bookingid");

        // Update booking
        BookingRequest updateRequest = new BookingRequest(
                "Updated",
                "User",
                2000,
                false,
                new BookingDates("2026-06-01", "2026-06-10"),
                "Lunch"
        );

        Response updateResponse = bookingService.updateBooking(
                bookingId,
                updateRequest,
                AuthService.getToken()
        );

        APIAssertions.verifyStatusCode(updateResponse, 200);
        APIAssertions.verifyField(updateResponse, "firstname", "Updated");
    }

    // 🔹 TC_04: Delete Booking with Valid ID
    @Test
    public void deleteBooking_validId() {

        BookingRequest request = new BookingRequest(
                "Delete",
                "Test",
                1500,
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                "Dinner"
        );

        Response createResponse = bookingService.createBooking(request);
        int bookingId = createResponse.jsonPath().getInt("bookingid");

        Response deleteResponse = bookingService.deleteBooking(
                bookingId,
                AuthService.getToken()
        );

        APIAssertions.verifyStatusCode(deleteResponse, 201);
    }

}
