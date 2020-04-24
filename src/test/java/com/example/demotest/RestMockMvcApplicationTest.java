package com.example.demotest;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class RestMockMvcApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greetingShouldReturnDefaultMessage1() throws Exception {
        String filePath = "user1";
        String url = "/users/1";

        JsonPath expected = JsonPath.from(getClass().getClassLoader().getResourceAsStream("__files/" + filePath));

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(expected.prettify()));
    }

    @Test
    public void greetingShouldReturnDefaultMessage2() throws Exception {
        String filePath = "user2";
        String url = "/users/2";

        JsonPath expected = JsonPath.from(getClass().getClassLoader().getResourceAsStream("__files/" + filePath));

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(expected.prettify()));
    }

    @Test
    public void greetingShouldReturnDefaultMessage3() throws Exception {
        String filePath = "users";
        String url = "/users";

        JsonPath expected = JsonPath.from(getClass().getClassLoader().getResourceAsStream("__files/" + filePath));

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(expected.prettify()));
    }
}
