package com.example.demotest.config;

import com.example.demotest.DemotestApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest(
        classes = DemotestApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class IntegrationTest {

}
