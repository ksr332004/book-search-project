package com.seran.auth.jwt;

import com.seran.entity.User;
import com.seran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Optional;

@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String token) {
        DecodedJWT decodedJWT = JwtUtil.tokenToJwt(token);
        if (decodedJWT == null) {
            throw new BadCredentialsException("Not used Token");
        }

        String email = decodedJWT.getClaim("email").asString();
        String role = decodedJWT.getClaim("role").asString();

        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new BadCredentialsException("Not exist email");
        } else if (user.get().getAvailable().equals("N")) {
            throw new BadCredentialsException("Not exist user");
        }

        return new JwtUser(user.get(), AuthorityUtils.createAuthorityList(role));
    }

}