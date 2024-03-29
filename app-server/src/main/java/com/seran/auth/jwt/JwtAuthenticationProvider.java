package com.seran.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;

    @Autowired
    public JwtAuthenticationProvider(JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl) {
        this.jwtUserDetailsServiceImpl = jwtUserDetailsServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("Bad token");
        }

        String token = authentication.getCredentials().toString().substring(7);

        if (JwtUtil.verify(token)) {
            UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(token);
            return new JwtAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}