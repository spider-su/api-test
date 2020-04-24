package com.example.demotest;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RestAssuredApplicationTests {
//
//    @LocalServerPort
//    private int port;

    private static RequestSpecification spec;

    @BeforeAll
    public static void setUp() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8080/")
                .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void givenUrl0_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        User user = new User(null, "Smt");
        User result = given()
                .spec(spec)
                .body(user)
                .when()
                .post("users")
                .then()
                .statusCode(200).extract().as(User.class);

        assertThat(result).isEqualToIgnoringGivenFields(user, "id");
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        given()
                .pathParam("id", 1)
                .when()
                .get("users/{id}")
                .then()
                .time(lessThan(1700L))
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("Alex"));
    }

    @Test
    public void givenUrl11_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() throws JSONException {
//        JsonPath body = given()
        ResponseBodyExtractionOptions body = given()
                .pathParam("id", 1)
                .when()
                .get("users/{id}")
                .then()
                .time(lessThan(1700L))
                .statusCode(200)
                .extract();
//                .statusCode(200).extract().body().jsonPath();
        String curlResponse = "{\"name\":\"Alex\",\"id\":1}";//input.readFromTextFile(System.getProperty("user.dir") + "\\src\\test\\resources\\inputFile\\CurlDataFile.txt");


        JSONAssert.assertEquals(curlResponse, body.asString(), false);
//        JsonPath.from(new File(getClass().getClassLoader().getResource("__files/users").getFile())).prettify()

        String filePath = "user1";
        JsonPath expected = JsonPath.from(getClass().getClassLoader().getResourceAsStream("__files/" + filePath));
        assertEquals(expected.prettify(), body.jsonPath().prettify());
    }

    @Test
    public void givenUrl1_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        given()
                .pathParam("id", 2)
                .when()
                .get("users/{id}")
                .then()
                .time(lessThan(50L))
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("Bob"));
    }

    @Test
    public void givenUrl2_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        List<User> users = given()
                .when().get("/users")
                .then().statusCode(200)
                .extract().body().jsonPath().getList(".", User.class);

        assertThat(users).contains(
                new User(1L, "Alex"),
                new User(2L, "Bob"));
//        assertThat(users, containsInAnyOrder(
//                new User(1L, "Alex"),
//                new User(2L, "Bob")));

        given()
                .when().get("/users")
                .then().statusCode(200)
                .assertThat()
                .body("name", hasItems("Alex", "Bob"));
    }
}
