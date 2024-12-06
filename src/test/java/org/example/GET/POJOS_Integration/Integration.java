package org.example.GET.POJOS_Integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.GET.Pojos.BookingDates;
import org.testng.annotations.Test;

public class Integration {

    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;
    private String token;
    private String bookingId;

    @Test
    public void generateToken() {
        AuthReq authReq = new AuthReq();
        authReq.setUsername("admin");
        authReq.setPassword("password123");

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/auth");
        requestSpecification.body(authReq);
        requestSpecification.contentType(ContentType.JSON);
        response = requestSpecification.when().log().all().post();
        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        System.out.println("Token is: " + response.asString());
        token = response.jsonPath().getString("token");
        System.out.println(token);

    }

    @Test(dependsOnMethods = "generateToken")
    public void create_Booking() {
        Booking booking = new Booking();
        System.out.println("Booking is in Progress please wait");

        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);


        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-05-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking");
        requestSpecification.body(booking);
        requestSpecification.contentType(ContentType.JSON);

        response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
        System.out.println("Here is the response: " + response.asString());
        System.out.println("Booking Successfull");
        bookingId = response.jsonPath().getString("bookingid");
        String name = response.jsonPath().getString("booking.firstname");
        System.out.println(bookingId);
        System.out.println(name);

    }

    @Test(dependsOnMethods = "create_Booking")
    public void updateBooking() {

        Booking booking = new Booking();
        System.out.println("The Booking is about to be updated");

        booking.setFirstname("Karan");
        booking.setLastname("Kundra");
        booking.setTotalprice(500);
        booking.setDepositpaid(true);


        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-11-01");
        bookingdates.setCheckout("2024-12-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Lunch");

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/" + bookingId);
        requestSpecification.body(booking);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);

        response = requestSpecification.when().put();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
        System.out.println("update Successfull");

    }

    @Test(dependsOnMethods = "updateBooking")
    public void deleteBooking() {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/" + bookingId);

        requestSpecification.cookie("token", token);

        response = requestSpecification.when().delete();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);
        System.out.println("Delete Successfully");
    }

    @Test(dependsOnMethods = "deleteBooking")
    public void verifyBooking() {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/" + bookingId);


        response = requestSpecification.when().get();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);
        System.out.println("No records found");

    }
}
