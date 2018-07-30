package com.seran.configuration;

import com.seran.auth.jwt.JwtAuthenticationFilter;
import com.seran.auth.jwt.JwtAuthenticationProvider;
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
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private JwtAuthenticationProvider jwtProvider;

    private static final String AUTH_ENTRY_POINT = "/api/auth/**";
    private static final String H2_ENTRY_POINT = "/h2-console/**";
    private static final String TOKEN_ENTRY_POINT = "/token";
    private static final String ERROR_ENTRY_POINT = "/error";

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/h2-console/**")
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtProvider);
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
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(patterns).permitAll()
                .antMatchers(TOKEN_ENTRY_POINT).permitAll()
                .antMatchers(AUTH_ENTRY_POINT).permitAll()
                .antMatchers(ERROR_ENTRY_POINT).permitAll()
                .anyRequest().authenticated();
        http
                .addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SkipPathRequestMatcher skipPathRequestMatcher() {
        return new SkipPathRequestMatcher(Arrays.asList(AUTH_ENTRY_POINT
                                                        , H2_ENTRY_POINT
                                                        , TOKEN_ENTRY_POINT
                                                        , ERROR_ENTRY_POINT));
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipPathRequestMatcher());
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

}