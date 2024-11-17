package org.example.trello2.controller;

import java.util.List;

import org.example.trello2.model.User;
import org.example.trello2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> buscaUser() {
        return userService.getAllUsers();
    }

    @PostMapping("/post")
    public User registerUser(@RequestBody User user) {
        return userService.createUser(user);
    }

}
