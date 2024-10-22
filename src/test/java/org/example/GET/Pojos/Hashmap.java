package org.example.GET.Pojos;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.ValidatableResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public class Hashmap {
    public static void main(String[] args) {
        RequestSpecification requestSpecification;
        ValidatableResponse validatableResponse;
        Response response;

    Map<String,Object> hasmap = new LinkedHashMap<>();
        hasmap.put("firstname","Keerti");
        hasmap.put("lastname", "Rakunde");
        hasmap.put("totalprice",165);
        hasmap.put("depositpaid",true);

        Map<String,Object> bookingdate = new LinkedHashMap<>();
        bookingdate.put("checkin","2018-01-01");
        bookingdate.put("checkout","2018-02-01");

        hasmap.put("bookingdates",bookingdate);
        hasmap.put("additionalneeds","Breakfast");

        System.out.println(hasmap);
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.body(hasmap);

        response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();
        System.out.println(response.asString());
         validatableResponse.statusCode(200);




}
}
