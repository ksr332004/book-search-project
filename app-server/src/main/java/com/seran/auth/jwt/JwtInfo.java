package com.seran.auth.jwt;

import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;

public class JwtInfo {

    public static final String HEADER_NAME = "Authorization";
    public static final String ISSUER = "issuer";
    public static final String TOKEN_KEY = "token.key";
    public static final long EXPIRES_LIMIT = 3L;

    public static Algorithm getAlgorithm() {
        try {
            return Algorithm.HMAC256(JwtInfo.TOKEN_KEY);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            return Algorithm.none();
        }
    }

}