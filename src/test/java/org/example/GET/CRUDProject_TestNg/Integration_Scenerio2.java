package org.example.GET.CRUDProject_TestNg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.AssertionsForClassTypes;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.requestSpecification;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;

public class Integration_Scenerio2 {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    public String bookingId;
    String token;

    @Test
    public void getToken() {
        String payload = "{    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"}";
        //Given
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/auth");
        requestSpecification.body(payload);
        requestSpecification.contentType(ContentType.JSON).log().all();
        response = requestSpecification.when().post();
        validatableResponse = response.then();
        validatableResponse.statusCode(200);

        token = response.jsonPath().getString("token");

    }


    @Test(dependsOnMethods = "getToken")
    public void post_request() {
        String payload = "{\"firstname\" : \"Bheem\",\n" +
                "    \"lastname\" : \"Patil\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "   }";
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking");

        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(payload).log().all();

        response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        bookingId = response.jsonPath().getString("bookingid");


    }


    @Test(dependsOnMethods = "post_request")
    public void put_request()
    {
        String payload_put ="{\"firstname\" : \"Karna\",\n" +
                "    \"lastname\" : \"Kumar\",\n" +
                "    \"totalprice\" : 105,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "   }";
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/"+bookingId);
        requestSpecification.body(payload_put).log().all();
        requestSpecification.cookie("token",token);
        requestSpecification.contentType(ContentType.JSON);
        response = requestSpecification.when().put();
        validatableResponse = response.then();
        validatableResponse.statusCode(200);

}

//
    @Test(dependsOnMethods ="put_request")
    public void delete_request()
    {
        System.out.println("The Deleted Operation Begins");


        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking"+bookingId);
        requestSpecification.cookie("token",token).log().all();
        response = requestSpecification.when().delete();
        validatableResponse = response.then();
        validatableResponse.statusCode(404);

        System.out.println("The Booking Deleted Successfully");
        System.out.println(bookingId);


    }
//
    @Test(dependsOnMethods = "delete_request")
    public void get_delete_afterdelete()
    {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking"+bookingId);
        requestSpecification.contentType(ContentType.JSON);
        response = requestSpecification.when().get();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);

        System.out.println("The Booking Deleted Successfully");


    }

}
