package com.APIwebsitebuilder.websitebuilder.service;


import com.APIwebsitebuilder.websitebuilder.dto.ReqRes;
import com.APIwebsitebuilder.websitebuilder.model.User;
import com.APIwebsitebuilder.websitebuilder.repository.UserRepository;
import com.APIwebsitebuilder.websitebuilder.responceHelper.SignInResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    public SignInResponse register(User user) {
       /* user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("register+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return userRepository.save(user);

        */


        SignInResponse resp = new SignInResponse();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User ourUserResult = userRepository.save(user);
            if (ourUserResult != null && !ourUserResult.getUsername().isEmpty()) {
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }


        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;

    }










    public SignInResponse login(String username, String password) {
       /* User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return user; // Authentication successful
        }

        */

        SignInResponse response = new SignInResponse();

        try {
            log.info("register+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                log.info("Authentication successful for user: {}", username);
            } catch (AuthenticationException e) {
                log.error("Authentication failed for user: {}", username, e);
                response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
                response.setError("Invalid ======================================credentials");
            }
            log.info("register+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            // Retrieve user from the repository
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            log.info("register+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            // Generate JWT token and refresh token
            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            // Set response properties
            response.setStatusCode(HttpStatus.OK.value());
            response.setToken(jwt);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");

        } catch (AuthenticationException e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setError("Invalid credentials");
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError("Internal server error");
        }
        return response;
        // Authentication failed
    }



    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        User users = userRepository.findByUsername(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }



}
