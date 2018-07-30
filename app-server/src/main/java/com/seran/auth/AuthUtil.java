package com.seran.auth;

import com.seran.auth.jwt.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public final class AuthUtil {

    public static Integer getUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            JwtUser jwtUser = (JwtUser)authentication.getPrincipal();
            return jwtUser.getId();
        }
        return null;
    }

    public static String getUserEmail(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            JwtUser jwtUser = (JwtUser)authentication.getPrincipal();
            return jwtUser.getUsername();
        }
        return null;
    }

}
