package com.seran.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seran.entity.User;
import com.seran.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    
    @GetMapping("/user")
    public ResponseEntity<User> getUser(Authentication authentication) {
        Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("/user/delete")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getPrincipal().toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
