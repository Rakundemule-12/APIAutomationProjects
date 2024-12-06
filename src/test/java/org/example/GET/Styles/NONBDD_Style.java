package org.example.GET.Styles;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class NONBDD_Style {
    public static void main(String[] args) {
    test2();
    test1();
    }

    private static void test2() {
        RequestSpecification r = RestAssured.given();
        r.baseUri("https://restful-booker.herokuapp.com");
        r.basePath("/booking/1454");
        r.when().get().then().log().all().statusCode(200).log().body();
    }

    private static void test1() {
        RequestSpecification r = RestAssured.given();
        r.baseUri("https://restful-booker.herokuapp.com");
        r.basePath("/booking/-1");
        r.when().get().then().log().all().statusCode(400).log().body();
    }

}
