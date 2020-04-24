package com.example.demotest;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    static Map<Long, User> users = new HashMap<>();
    static {
        users.put(1L, new User(1L, "Alex"));
        users.put(2L, new User(2L, "Bob"));
    }

    @PostMapping
    User getById(@RequestBody User user) {
        long id = users.size() + 1;
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @GetMapping("{id}")
    User getById(@PathVariable Long id) {
        return users.get(id);
    }

    @PutMapping("{id}")
    void putById(@PathVariable Long id, @RequestParam(value = "name") String name) {
        users.get(id).name = name;
    }

    @GetMapping
    Collection<User> getAll() {
        return users.values();
    }
}
