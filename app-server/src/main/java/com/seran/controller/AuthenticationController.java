package com.seran.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seran.auth.BeforeSecurityUser;
import com.seran.auth.UserDetailsImpl;
import com.seran.auth.jwt.JwtInfo;
import com.seran.auth.jwt.JwtUtil;
import com.seran.entity.User;
import com.seran.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<Void> postUser(@Valid @RequestBody User registUser) {
        Optional<User> user = userService.searchUserByEmail(registUser.getEmail());
        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.saveUser(registUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody BeforeSecurityUser beforeSecurityUser) {
        Optional<User> user = userService.searchUserByEmail(beforeSecurityUser.getEmail());
        if (user.isPresent()) {
            if (bCryptPasswordEncoder.matches(beforeSecurityUser.getPassword(), user.get().getPassword())) {
                List<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.get().getRole());
                UserDetails userDetails = new UserDetailsImpl(beforeSecurityUser, grantedAuths);
                String token = JwtUtil.createToken(userDetails);
                HttpHeaders headers = new HttpHeaders();
                headers.add(JwtInfo.HEADER_NAME, token);
                return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/refresh")
    public ResponseEntity<String> refreshToken(Authentication authentication) {
        UserDetails userDetails = new UserDetailsImpl(authentication.getPrincipal().toString(), new ArrayList<>(authentication.getAuthorities()));
        String token = JwtUtil.refreshToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtInfo.HEADER_NAME, token);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    
    
}
