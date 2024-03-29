package com.seran.controller;

import com.seran.auth.AuthUtil;
import com.seran.auth.BeforeSecurityUser;
import com.seran.auth.jwt.JwtInfo;
import com.seran.auth.jwt.JwtUser;
import com.seran.auth.jwt.JwtUtil;
import com.seran.entity.User;
import com.seran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticationController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> postUser(@Valid @RequestBody User registrationUser) {
        Optional<User> user = userService.searchUserByEmail(registrationUser.getEmail());
        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.saveUser(registrationUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ModelMap> login(@Valid @RequestBody BeforeSecurityUser beforeSecurityUser) {
        Optional<User> user = userService.searchUserByEmail(beforeSecurityUser.getEmail());
        if (user.isPresent() && ("Y").equals(user.get().getAvailable())) {
            if (bCryptPasswordEncoder.matches(beforeSecurityUser.getPassword(), user.get().getPassword())) {
                List<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.get().getRole());
                UserDetails userDetails = new JwtUser(user.get(), grantedAuths);
                String token = JwtUtil.createToken(userDetails);
                return new ResponseEntity<>(new ModelMap(JwtInfo.HEADER_NAME, token), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/refresh")
    public ResponseEntity<ModelMap> refreshToken(Authentication authentication) {
        Optional<User> user = userService.searchUserByEmail(AuthUtil.getUserEmail(authentication));
        if (user.isPresent()) {
            UserDetails userDetails = new JwtUser(user.get(), new ArrayList<>(authentication.getAuthorities()));
            String token = JwtUtil.refreshToken(userDetails);
            return new ResponseEntity<>(new ModelMap(JwtInfo.HEADER_NAME, token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    
    
}
