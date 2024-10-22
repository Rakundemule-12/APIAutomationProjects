package org.example.GET.Styles;

import io.restassured.RestAssured;

public class BDD_Style {
    public static void main(String[] args) {
        System.out.println("Rest Assured Test Case");
        System.out.println("Get Request Demo");
        Test1();
        Test2();


    }

    private static void Test1() {
        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/1736")
                .when().log().all().get()
                .then().log().all()
                .statusCode(200);
    }

    private static void Test2() {
        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/1736")
                .when().log().all().get()
                .then().log().all()
                .statusCode(200);
    }
}
