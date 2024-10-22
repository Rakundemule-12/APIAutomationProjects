package org.example.GET.Pojos;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Dserializtion {
    RequestSpecification requestSpecification = RestAssured.given();
    ValidatableResponse validatableResponse;
    Response response;


    @Test
    public void nonbdd_post()
    {
        System.out.println("Booking is in Progress please wait");

        Booking booking = new Booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);


        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-05-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

//        String baseUrl = "https://restful-booker.herokuapp.com";
//        String basePath= "/booking";



        Gson gson = new Gson();
        String jsonstringpayload = gson.toJson(booking);
        System.out.println(jsonstringpayload);


        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON).log().all();
        requestSpecification.body(jsonstringpayload);



        response = requestSpecification.when().log().all().post();
        String responseString = response.asString();
        System.out.println(responseString);

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        //Desrializtion
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        System.out.println(bookingResponse.getBookingid());
        System.out.println(bookingResponse.getBooking().getFirstname());

        assertThat(bookingResponse.getBooking(). getFirstname()).isEqualTo("James").isNotEmpty();


}
}
