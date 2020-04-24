package com.example.demotest;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestMvcApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        User user = new User(1L, "Alex");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "users/1",
                User.class)).isEqualTo(user);
    }

    @Test
    public void greetingShouldReturnDefaultMessage2() throws Exception {
        User user = new User(2L, "Bob");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "users/2",
                User.class)).isEqualTo(user);
    }

    @Test
    public void greetingShouldReturnDefaultMessage3() throws Exception {
        User user1 = new User(1L, "Alex");
        User user2 = new User(2L, "Bob");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "users",
                User.class)).isEqualTo(Lists.newArrayList(user1, user2));
    }
}