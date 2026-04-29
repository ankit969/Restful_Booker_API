package bdd.stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.request.BookingDates;
import models.request.BookingRequest;
import reporting.ExtentLogger;
import services.AuthService;
import services.BookingService;
import utils.FakerUtils;

public class BookingSteps {
	
	BookingService bookingService = new BookingService();

    BookingRequest request;
    Response response;
    int bookingId;

    // ===============================
    // 🔹 CREATE BOOKING
    // ===============================

    @Given("user has valid booking data")
    public void user_has_valid_booking_data() {

        request = new BookingRequest(
                FakerUtils.getFirstName(),
                FakerUtils.getLastName(),
                FakerUtils.getPrice(),
                true,
                new BookingDates("2026-05-01", "2026-05-05"),
                FakerUtils.getAdditionalNeeds()
        );
    }

    @When("user creates a booking")
    public void user_creates_a_booking() {
    	ExtentLogger.logRequest("Creating booking");

        response = bookingService.createBooking(request);
        ExtentLogger.logResponse("Response: " + response.asString());
        
        bookingId = response.jsonPath().getInt("bookingid");
    }

    @Then("booking should be created successfully")
    public void booking_should_be_created_successfully() {

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(bookingId);
    }

    // ===============================
    // 🔹 COMMON PRECONDITION
    // ===============================

    @Given("a booking already exists")
    public void a_booking_already_exists() {

        user_has_valid_booking_data();
        user_creates_a_booking();
    }

    // ===============================
    // 🔹 GET BOOKING
    // ===============================

    @When("user retrieves the booking")
    public void user_retrieves_the_booking() {

        response = bookingService.getBooking(bookingId);
    }

    @Then("booking details should be correct")
    public void booking_details_should_be_correct() {

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(
                response.jsonPath().getString("firstname"),
                request.getFirstname()
        );
    }

    // ===============================
    // 🔹 UPDATE BOOKING
    // ===============================

    @When("user updates the booking")
    public void user_updates_the_booking() {

        BookingRequest updatedRequest = new BookingRequest(
                "Updated",
                "User",
                2000,
                false,
                new BookingDates("2026-06-01", "2026-06-10"),
                "Lunch"
        );

        response = bookingService.updateBooking(
                bookingId,
                updatedRequest,
                AuthService.getToken()
        );
    }

    @Then("booking should be updated")
    public void booking_should_be_updated() {

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(
                response.jsonPath().getString("firstname"),
                "Updated"
        );
    }

    // ===============================
    // 🔹 DELETE BOOKING
    // ===============================

    @When("user deletes the booking")
    public void user_deletes_the_booking() {

        response = bookingService.deleteBooking(
                bookingId,
                AuthService.getToken()
        );
    }

    @Then("booking should be deleted")
    public void booking_should_be_deleted() {

        Assert.assertEquals(response.getStatusCode(), 201);
    }

}
