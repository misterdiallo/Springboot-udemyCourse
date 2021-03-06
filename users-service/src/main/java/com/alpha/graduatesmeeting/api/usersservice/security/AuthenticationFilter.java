package com.alpha.graduatesmeeting.api.usersservice.security;
import com.alpha.graduatesmeeting.api.usersservice.model.LoginUserRequestModel;
import com.alpha.graduatesmeeting.api.usersservice.services.UsersService;
import com.alpha.graduatesmeeting.api.usersservice.shared.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UsersService usersService;
    private Environment env;

    public AuthenticationFilter(
            UsersService usersService,
            Environment env,
            AuthenticationManager authenticationManager
    ) {
        this.usersService = usersService;
        this.env =  env;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws AuthenticationException {
        try {
            LoginUserRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginUserRequestModel.class);
            return  getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUserId(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth
    ) throws IOException, ServletException  {
        String userName =  ((User) auth.getPrincipal()).getUsername();
        UserDTO userDetails = usersService.getUserDetailsByUserId(userName);
        System.out.println(Long.parseLong(env.getProperty("token.expiration_time")));
        System.out.println(env.getProperty("token.secret"));
        String token = Jwts.builder()
                   .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))
                .compact();

        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId());
    }
}
