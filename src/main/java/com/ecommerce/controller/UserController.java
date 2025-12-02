package com.ecommerce.controller;

import com.ecommerce.dto.userDTO.LoginRequestDTO;
import com.ecommerce.dto.userDTO.RequestUser;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;
import com.ecommerce.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RequestUser user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequestDTO user) {
        return userService.login(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable long id) {
        return userService.userById(id);
    }

    @GetMapping("/authenticatedUser")
    public ResponseEntity<ApiResponse> getAuthenticatedUser() {
        return userService.authenticatedUser();
    }

}
