package com.workintech.twitter.controller;

import com.workintech.twitter.dto.RegistrationRequest;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;
    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest registrationRequest){
       return authenticationService.register(
               registrationRequest.firstName()
               ,registrationRequest.lastName()
               ,registrationRequest.username()
               ,registrationRequest.email()
               ,registrationRequest.password());
    }
}
