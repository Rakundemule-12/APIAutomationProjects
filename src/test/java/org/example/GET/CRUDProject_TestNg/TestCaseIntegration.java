package org.example.GET.CRUDProject_TestNg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestCaseIntegration {
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;
    String token;
    String Bookingid;

    public String getToken()
    {
        String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "} ";
        //Given
        RequestSpecification r = RestAssured.given();
        r.baseUri("https://restful-booker.herokuapp.com");
        r.basePath("/auth");
        r.contentType(ContentType.JSON).log().all();
        r.body(payload);

        //When - Response
        Response response = r.when().post();

        //Then - ValidatableResponse
        //Validation
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);

        //Extract The token
        token = response.jsonPath().getString("token");
        System.out.println(token);
        return token;
    }

    @Test(dependsOnMethods = "getToken")
    public String getBookingId()
    {
        String payload_POST = "{\n" +
                "          \"firstname\": \"Josh\",\n" +
                "          \"lastname\": \"Allen\",\n" +
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
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.body(payload_POST).log().all();

        Response response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        Bookingid = response.jsonPath().getString("bookingid");
        System.out.println(Bookingid);
        return Bookingid;
    }

    @Test
    public void test_update_request_put()
    {
        token = getToken();
        Bookingid = getBookingId();

        String payload_POST = "{\n" +
                "          \"firstname\": \"Krishna\",\n" +
                "          \"lastname\": \"Lahari\",\n" +
                "          \"totalprice\": 400,\n" +
                "          \"depositpaid\": false ,\n" +
                "          \"bookingdates\": {\n"  +
                "           \"checkin\": \"2020-01-01\",\n" +
                "           \"checkout\": \"2020-02-01\" \n" +
                "    }, \n" +
                "          \"additionalneeds\": \"Lunch\"\n" +
                "}";
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking/"+Bookingid);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token",token);
        requestSpecification.body(payload_POST).log().all();

        Response response = requestSpecification.when().put();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

//        Bookingid = response.jsonPath().getString("bookingid");
    }

    @Test(dependsOnMethods ="test_update_request_put")
    public void test_update_request_get()
    {
        System.out.println("The Get Request");
        System.out.println("The Booking ID is"+Bookingid);
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking/"+Bookingid);
        requestSpecification.contentType(ContentType.JSON);


        response = requestSpecification.get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        String firstname = response.then().extract().path("firstname");
        String lastname = response.then().extract().path("lastname");

        assertThat(firstname).isEqualTo("Krishna").isNotEmpty().isNotBlank();
        assertThat(lastname).isEqualTo("Lahari").isNotEmpty().isNotBlank();
        System.out.println("Completed the Get Request");
    }

    @Test(dependsOnMethods = "test_update_request_get")
    public void test_delete_booking()
    {
        System.out.println("The Deleted Operation Begins");
        String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "} ";

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking/"+Bookingid);
        requestSpecification.cookie("token",token).log().all();
        response = requestSpecification.when().delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);

        System.out.println("The Booking Deleted Successfully");

    }
    @Test(dependsOnMethods = "test_delete_booking")
    public void test_after_delete_request_get()
    {

        System.out.println("After Delete Request");
        System.out.println("The Booking ID is"+Bookingid);
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking/"+Bookingid);
        requestSpecification.contentType(ContentType.JSON);


        response = requestSpecification.get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);
        System.out.println("The Booking Record No longer Exists");
    }
}
