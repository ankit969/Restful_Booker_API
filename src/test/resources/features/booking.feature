Feature: Booking API

  Scenario: Create a booking successfully
    Given user has valid booking data
    When user creates a booking
    Then booking should be created successfully

  Scenario: Get booking by ID
    Given a booking already exists
    When user retrieves the booking
    Then booking details should be correct

  Scenario: Update booking successfully
    Given a booking already exists
    When user updates the booking
    Then booking should be updated

  Scenario: Delete booking successfully
    Given a booking already exists
    When user deletes the booking
    Then booking should be deleted