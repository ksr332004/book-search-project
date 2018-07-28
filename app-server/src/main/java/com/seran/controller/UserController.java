package com.seran.controller;

import com.seran.auth.AuthUtil;
import com.seran.entity.User;
import com.seran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    @DeleteMapping("/user/delete")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        Integer userId = AuthUtil.getUserId(authentication);
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
