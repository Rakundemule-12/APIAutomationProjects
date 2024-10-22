package org.example.GET.Testng;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class Get {

    @Test
    public void test_get() {

        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/617")
                .when().log().all().get()
                .then().log().all()
                .statusCode(200);
    }
}
