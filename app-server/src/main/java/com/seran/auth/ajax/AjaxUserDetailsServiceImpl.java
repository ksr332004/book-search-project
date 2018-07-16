package com.seran.auth.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.seran.auth.UserDetailsImpl;
import com.seran.entity.User;
import com.seran.repository.UserRepository;

@Component
public class AjaxUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "라는 사용자가 없습니다."));
        return new UserDetailsImpl(email, AuthorityUtils.createAuthorityList(user.getRole()));
    }

}