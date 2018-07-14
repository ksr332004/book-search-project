package com.seran.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seran.model.User;
import com.seran.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    
    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/registration")
    public ResponseEntity<Void> postRegistration(@RequestBody User user) {
    	userService.saveUser(user);
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}
