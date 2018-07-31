package com.seran.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seran.auth.BeforeSecurityUser;
import com.seran.auth.jwt.JwtUser;
import com.seran.auth.jwt.JwtUtil;
import com.seran.entity.User;
import com.seran.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthenticationController.class, secure = false)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    User user = new User();
    User newUser = new User();
    BeforeSecurityUser beforeSecurityUser = new BeforeSecurityUser();

    @Before
    public void setUpUser() throws Exception {
        user.setEmail("test11@test.com");
        user.setPassword("test1234");
        user.setName("Test");

        newUser.setId(100);
        newUser.setEmail("test@test.com");
        newUser.setPassword("test12345");
        newUser.setName("test");
        newUser.setRole("ROLE_USER");
    }

    @Test
    public void signup_test() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void login_test() throws Exception {
        given(userService.searchUserByEmail("test@test.com")).willReturn(java.util.Optional.of(newUser));

        UserDetails userDetails = new JwtUser(newUser, AuthorityUtils.commaSeparatedStringToAuthorityList(newUser.getRole()));
        String token = JwtUtil.createToken(userDetails);

        beforeSecurityUser.setEmail("test@test.com");
        beforeSecurityUser.setPassword("test12345");

        mockMvc.perform(post("/api/auth/login")
                .content(asJsonString(beforeSecurityUser))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
