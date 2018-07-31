package com.seran.controller;

import com.seran.auth.AuthUtil;
import com.seran.entity.User;
import com.seran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/info")
    public ResponseEntity<User> getUser(Authentication authentication) {
        Integer userId = AuthUtil.getUserId(authentication);
        Optional<User> user = userService.searchUserById(userId);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping("/user/update")
    public ResponseEntity<Void> updateUser(Authentication authentication, @RequestBody User updateUser) {
        Integer userId = AuthUtil.getUserId(authentication);
        userService.updateUser(userId, updateUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/delete")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        Integer userId = AuthUtil.getUserId(authentication);
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
