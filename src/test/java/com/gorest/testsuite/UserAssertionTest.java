package com.gorest.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserAssertionTest {
    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "https://gorest.co.in";

        response = given()
                .queryParam("page", 1)
                .queryParam("per_page", 20)
                .when()
                .get("/public/v2/users")
                .then().statusCode(200);
    }
    //1. Verify the if the total record is 20
    @Test
    public void test001() {
        response.body("size()", equalTo(20));
    }

    //2. Verify the if the name of id = 7015022 is equal to ”Mrs. Vimal Sharma Agarwal”
    @Test
    public void test002() {
        response.body("findAll{it.id==7015022}.name", hasItem("Mrs. Vimal Sharma"));
    }

    //3. Check the single ‘Name’ in the Array list (Sujata Khan)
    @Test
    public void test003() {
        response.body("name", hasItem("Sujata Khan"));
    }

    //4. Check the multiple ‘Names’ in the ArrayList (Keerti Trivedi, Naveen Abbott DVM, Gudakesa Gill )
    @Test
    public void test004() {
        response.body("name", hasItems("Keerti Trivedi", "Naveen Abbott DVM", "Gudakesa Gill"));
    }

    //5. Verify the email of userid = 7015019 is equal “msgr_aagneya_gandhi@dibbert.example”
    @Test
    public void test005() {
        response.body("find{it.id == 7015019}.email", equalTo("msgr_aagneya_gandhi@dibbert.example"));
    }

    //6. Verify the status is “Active” of user name is “Hiranya Prajapat Agarwal”
    @Test
    public void test006() {
        response.body("find{it.status == 'active'}.name", equalTo("Hiranya Prajapat"));
    }

    //7. Verify the Gender = male
    @Test
    public void test007() {
        response.body("find{it.name == 'Mrs. Vimal Sharma'}.gender", equalTo("male"));
    }
}