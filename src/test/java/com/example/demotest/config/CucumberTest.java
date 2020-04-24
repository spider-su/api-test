package com.example.demotest.config;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;

@Slf4j
@RunWith(Cucumber.class)
@CucumberOptions(
//        plugin = {"pretty"},
        features = {"src/test/resources"},
        glue = {"com.example.demotest"}
)
public class CucumberTest extends IntegrationTest {

}
