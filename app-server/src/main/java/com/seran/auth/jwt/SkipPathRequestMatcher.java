package com.seran.auth.jwt;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher {

    private OrRequestMatcher skipRequestMatcher;
    
    public SkipPathRequestMatcher(List<String> skipPathList) {
        if(!skipPathList.isEmpty()) {
            List<RequestMatcher> requestMatcherList = skipPathList.stream()
                                                                    .map(AntPathRequestMatcher::new)
                                                                    .collect(Collectors.toList());
            requestMatcherList.add(new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name()));
            skipRequestMatcher = new OrRequestMatcher(requestMatcherList);
        }
    }
    
    @Override
    public boolean matches(HttpServletRequest request) {
        return !skipRequestMatcher.matches(request);
    }
}