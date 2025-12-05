package com.ecommerce.service;

import com.ecommerce.dto.userDTO.LoginRequestDTO;
import com.ecommerce.dto.userDTO.LoginResponseDTO;
import com.ecommerce.dto.userDTO.RequestUser;
import com.ecommerce.dto.userDTO.UpdateUserDto;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.entity.sec.CustomUserDetails;
import com.ecommerce.exception.ExistedUserException;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.sec.JWTService;
import com.ecommerce.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.crypto.SecretKey;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;
    private final JWTService jwtService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<ApiResponse> addUser(RequestUser requestUser) {
        User user = userMapper.toUser(requestUser);
        if(userRepository.findByUsername(requestUser.getUsername()).isPresent()){
            throw new ExistedUserException("Username already exists : " + requestUser.getUsername());
        }
        if(userRepository.findByEmail(requestUser.getEmail()).isPresent()){
            throw new ExistedUserException("Email already exists : " + requestUser.getEmail());
        }
        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("ROLE_CUSTOMER"));
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(savedUser);
        savedUser.setCart(cart);

        userRepository.save(savedUser);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername(savedUser.getUsername());
        loginRequestDTO.setPassword(requestUser.getPassword());
        return login(loginRequestDTO);

    }

    public ResponseEntity<ApiResponse> login(LoginRequestDTO user) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            CustomUserDetails authUser = (CustomUserDetails) authentication.getPrincipal();
            LoginResponseDTO res =  new LoginResponseDTO(getUser(authUser.getUsername()), jwtService.generateToken(user.getUsername()));
            ApiResponse apiResponse = new ApiResponse(res,"Login done",(HttpStatus.OK).value());
            return ResponseEntity.ok(apiResponse);
        }
        ApiResponse apiResponse = new ApiResponse(null,"Username or password incorrect", (HttpStatus.UNAUTHORIZED).value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    public ResponseEntity<ApiResponse> removeUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with username: " + userId));
        userRepository.delete(user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(user, "User deleted successfully", HttpStatus.OK.value()), HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse> userById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with Id : " + id));
        ApiResponse apiResponse = new ApiResponse(user, "Ok", (HttpStatus.OK).value());
        return ResponseEntity.ok(apiResponse);

    }

    public ResponseEntity<ApiResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        ApiResponse apiResponse = new ApiResponse(getUser(user.getUsername()), "Ok", (HttpStatus.OK).value());
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<ApiResponse> updateUser(long userId, UpdateUserDto userData) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with Id : " + userId));
        userMapper.updateRequestToUser(userData, user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(user, "Ok", (HttpStatus.OK).value()));
    }
}
