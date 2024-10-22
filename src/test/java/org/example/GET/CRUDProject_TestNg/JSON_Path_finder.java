package org.example.GET.CRUDProject_TestNg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JSON_Path_finder {
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;
    @Test
    public void test_post()
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

        requestSpecification.body(payload_POST);

        response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();
        System.out.println(response.asString());
//      validatableResponse.statusCode(200);


        // Extracting the values from JSON

        JsonPath jsonPath = new JsonPath(response.asString());
        String bookingid = jsonPath.getString("bookingid");
        String firstname = jsonPath.getString("booking.firstname");
        String checkout =  jsonPath.getString("booking.bookingdates.checkout");
        System.out.println(bookingid);
        System.out.println(firstname);
        System.out.println(checkout);

        assertThat(bookingid).isNotNull().isNotBlank();
        assertThat(firstname).isNotNull().isNotBlank().isEqualTo("Josh");
        assertThat(checkout).isNotNull().isNotBlank();

    }
}
