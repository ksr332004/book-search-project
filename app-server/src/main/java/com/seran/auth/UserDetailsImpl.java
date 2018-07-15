package com.seran.auth;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    public UserDetailsImpl(String email, List<GrantedAuthority> authorities) {
        super(email, "", authorities);
    }

    public UserDetailsImpl(BeforeSecurityUser beforeSecurityUser, List<GrantedAuthority> authorities) {
        super(beforeSecurityUser.getEmail(), beforeSecurityUser.getPassword(), authorities);
    }
}