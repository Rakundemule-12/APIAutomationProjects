package org.example.GET.POJOS2;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.GET.Pojos.BookingDates;
import org.example.GET.Pojos.BookingResponse;
import org.testng.annotations.Test;

@Test
public class Serialization {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;

    public void put_request()
    {
        Booking booking = new Booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2024-02-01");
        bookingDates.setCheckout("2024-05-01");
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(booking);

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking");
        requestSpecification.body(jsonPayload);
        requestSpecification.contentType(ContentType.JSON);

        response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
        response.asString();
        System.out.println("Booking Successfull");


    }
}
