package tdd.booking;

import org.testng.annotations.Test;

import assertions.APIAssertions;
import base.BaseTest;
import io.restassured.response.Response;
import models.request.BookingDates;
import models.request.BookingRequest;
import services.AuthService;
import services.BookingService;

public class BookingNegativeTest extends BaseTest{
	
	BookingService bookingService = new BookingService();

    // 🔹 TC_01: Create Booking with Missing Firstname
    @Test
    public void createBooking_missingFirstname() {

        BookingRequest request = new BookingRequest(
                null,
                "Test",
                1000,
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                "Breakfast"
        );

        Response response = bookingService.createBooking(request);

        // Restful Booker may still return 200 → validate logically
        APIAssertions.verifyStatusCode(response, 200);
    }

    // 🔹 TC_02: Create Booking with Invalid Price
    @Test
    public void createBooking_invalidPrice() {

        BookingRequest request = new BookingRequest(
                "Ankit",
                "Test",
                -100,
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                "Lunch"
        );

        Response response = bookingService.createBooking(request);

        APIAssertions.verifyStatusCode(response, 200);
    }

    // 🔹 TC_03: Get Booking with Invalid ID
    @Test
    public void getBooking_invalidId() {

        Response response = bookingService.getBooking(99999999);

        APIAssertions.verifyStatusCode(response, 404);
    }

    // 🔹 TC_04: Update Booking without Auth
    @Test
    public void updateBooking_withoutAuth() {

        BookingRequest request = new BookingRequest(
                "Test",
                "User",
                1000,
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                "Dinner"
        );

        // Passing null token intentionally
        Response response = bookingService.updateBooking(1, request, null);

        APIAssertions.verifyStatusCode(response, 403);
    }

    // 🔹 TC_05: Delete Booking without Auth
    @Test
    public void deleteBooking_withoutAuth() {

        Response response = bookingService.deleteBooking(1, null);

        APIAssertions.verifyStatusCode(response, 403);
    }

    // 🔹 TC_06: Delete Booking with Invalid ID
    @Test
    public void deleteBooking_invalidId() {

        Response response = bookingService.deleteBooking(
                999999,
                AuthService.getToken()
        );

        int statusCode = response.getStatusCode();

        APIAssertions.assertTrue(
                statusCode == 404 || statusCode == 405,
                "Unexpected status code for invalid delete"
        );
    }

}
