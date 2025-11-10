package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.entity.sec.CustomUserDetails;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.sec.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CartService cartService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private RoleService roleService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Cart cart = new Cart();
        cart.setUser(user);
        Cart finalCart = cartService.createCart(cart);
        user.setCart(finalCart);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("ROLE_ADMIN"));
        roles.add(roleService.findRoleByName("ROLE_CUSTOMER"));
        user.setRoles(roles);
        return userRepository.save(user);
    }
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);
        return user;
    }

    public String login(User user) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "fail";
    }

    public User userById(long id) {
        try {
            return userRepository.findById(id);
        }  catch (Exception e) {
            return null;
        }

    }

    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        User authUser = getUser(user.getUsername());


        return authUser;
    }
}
