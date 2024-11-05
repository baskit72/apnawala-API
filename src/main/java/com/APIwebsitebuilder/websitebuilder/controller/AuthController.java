package com.APIwebsitebuilder.websitebuilder.controller;

import com.APIwebsitebuilder.websitebuilder.model.User;
import com.APIwebsitebuilder.websitebuilder.requestHelper.LoginRequest;
import com.APIwebsitebuilder.websitebuilder.responceHelper.SignInResponse;
import com.APIwebsitebuilder.websitebuilder.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        SignInResponse signInResp= authService.register(user);
        return ResponseEntity.ok(signInResp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("===================================================================");
        SignInResponse signInResp= authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        log.info("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999==================================================================="+signInResp);

        return ResponseEntity.ok(signInResp);
    }
}
