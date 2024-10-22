package org.example.GET.Styles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class NONBDD_PUT {
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;

    @Test
    public void Test_case()
    {
        String token= "cecd2d41660510e";
        String booking_id = "1362";
        String payloadPUT = "{\n" +
              "          \"firstname\": \"Karan\",\n" +
                "          \"lastname\": \"Johar\",\n" +
                "          \"totalprice\": 212,\n" +
                "          \"depositpaid\": false ,\n" +
                "          \"bookingdates\": {\n"  +
                "           \"checkin\": \"2020-01-01\",\n" +
                "           \"checkout\": \"2020-02-01\" \n" +
                "    }, \n" +
                "          \"additionalneeds\": \"Lunch\"\n" +
                "}";
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking/"+booking_id);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token",token);
        requestSpecification.body(payloadPUT).log().all();

        Response response = requestSpecification.when().put();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

    }

}
