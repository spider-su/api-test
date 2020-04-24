package com.example.demotest.steps;

import com.example.demotest.User;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class Steps {
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    @Given("user with id (\\d+)$")
    public void user_with_id(Long id){
        request = given()
                .pathParam("id", id);
    }

    @When("^the client calls (.*) (\\d+)$")
    public void theClientCallsUsers(String path, Long id) {
        response = given().pathParam("id", id)
                .when().get(path);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        json = response.then()
//                .time(lessThan(1700L))
                .statusCode(statusCode);
    }

    @And("^the client receives name (.*)$")
    public void the_client_receives_server_version_body(String name) throws Throwable {
        json.assertThat()
                .body("name", equalTo(name));
    }

    @And("^the response like JSON (.*)$")
    public void theResponseLikeJSONUsers(String filePath) {
        JsonPath expected = JsonPath.from(getClass().getClassLoader().getResourceAsStream("__files/" + filePath));
        assertEquals(expected.prettify(), json.extract().body().jsonPath().prettify());
    }
}
