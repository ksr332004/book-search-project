package com.seran.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seran.auth.BaseSecurityHandler;
import com.seran.auth.ajax.AjaxAuthenticationFilter;
import com.seran.auth.ajax.AjaxAuthenticationProvider;
import com.seran.auth.ajax.AjaxUserDetailsServiceImpl;
import com.seran.auth.jwt.JwtAuthenticationFilter;
import com.seran.auth.jwt.JwtAuthenticationProvider;
import com.seran.auth.jwt.JwtUserDetailsServiceImpl;
import com.seran.auth.jwt.SkipPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private JwtAuthenticationProvider jwtProvider;
    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsServiceImple;
    @Autowired
    private AjaxAuthenticationProvider ajaxProvider;
    @Autowired
    private AjaxUserDetailsServiceImpl ajaxUserDetailsServiceImple;
    @Autowired
    private BaseSecurityHandler securityHandler;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String LOGIN_ENTRY_POINT = "/api/auth/login";
    private static final String REGISTRATION_ENTRY_POINT = "/api/auth/**";
    private static final String TOKEN_ENTRY_POINT = "/token";
    private static final String ERROR_ENTRY_POINT = "/error";

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxProvider)
                .authenticationProvider(jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] patterns = new String[] {
                "/",
                "/*.html",
                "/**/*.html"
        };

        http
                .csrf()
                .disable()
//                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(patterns).permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .antMatchers(TOKEN_ENTRY_POINT).permitAll()
                .antMatchers(REGISTRATION_ENTRY_POINT).permitAll()
                .antMatchers(LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(ERROR_ENTRY_POINT).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public AntPathRequestMatcher antPathRequestMatcher() {
        return new AntPathRequestMatcher(TOKEN_ENTRY_POINT, HttpMethod.POST.name());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter(antPathRequestMatcher(), objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(securityHandler);
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

    @Bean
    public SkipPathRequestMatcher skipPathRequestMatcher() {
        return new SkipPathRequestMatcher(Arrays.asList(REGISTRATION_ENTRY_POINT, LOGIN_ENTRY_POINT, TOKEN_ENTRY_POINT, ERROR_ENTRY_POINT));
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipPathRequestMatcher());
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

}