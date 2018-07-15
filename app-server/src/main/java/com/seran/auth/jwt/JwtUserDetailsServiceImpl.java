package com.seran.auth.jwt;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.seran.auth.UserDetailsImpl;

@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String token) {
        DecodedJWT decodedJWT = JwtUtil.tokenToJwt(token);

        if (decodedJWT == null) {
            throw new BadCredentialsException("Not used Token");
        }

        String email = decodedJWT.getClaim("email").asString();
        String role = decodedJWT.getClaim("role").asString();

        return new UserDetailsImpl(email, AuthorityUtils.createAuthorityList(role));
    }

}