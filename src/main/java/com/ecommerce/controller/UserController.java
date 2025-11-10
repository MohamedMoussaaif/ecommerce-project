package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userService.addUser(user);
        return "User created successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {

        return userService.login(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public User getUserById(@RequestParam long id) {
        return userService.userById(id);
    }

    @GetMapping("/authenticatedUser")
    public User getAuthenticatedUser() {
        return userService.authenticatedUser();
    }

}
