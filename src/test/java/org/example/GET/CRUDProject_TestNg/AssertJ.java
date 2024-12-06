package org.example.GET.CRUDProject_TestNg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Test
public class AssertJ {
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;
    String Bookingid;

    public void test_post() {
        String payload_POST = "{\n" +
                "          \"firstname\": \"Josh\",\n" +
                "          \"lastname\": \"Allen\",\n" +
                "          \"totalprice\": 212,\n" +
                "          \"depositpaid\": false ,\n" +
                "          \"bookingdates\": {\n" +
                "           \"checkin\": \"2020-01-01\",\n" +
                "           \"checkout\": \"2020-02-01\" \n" +
                "    }, \n" +
                "          \"additionalneeds\": \"Lunch\"\n" +
                "}";
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.body(payload_POST).log().all();

        response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        Bookingid = response.jsonPath().getString("bookingid");
        System.out.println("Here is the bookingID "+Bookingid);

        Integer Bookingid1 = response.then().extract().path("bookingid");
        String firstname = response.then().extract().path("booking.firstname");
        System.out.println("Verified "+Bookingid1);

        assertThat(Bookingid1).isNotNull().isNotZero().isPositive();
        assertThat(firstname).isEqualTo("Josh").isNotEmpty().isNotBlank();

    }
}
