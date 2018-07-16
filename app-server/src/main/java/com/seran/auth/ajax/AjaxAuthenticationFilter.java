package com.seran.auth.ajax;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seran.entity.User;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public AjaxAuthenticationFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper) {
        super(requestMatcher);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (isJson(request)) {
            User user = objectMapper.readValue(request.getReader(), User.class);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            return getAuthenticationManager().authenticate(authentication);
        } else {
            throw new AccessDeniedException("Don't use content type for " + request.getContentType());
        }
    }

    private boolean isJson(HttpServletRequest request) {
        return MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(request.getContentType());
    }
}