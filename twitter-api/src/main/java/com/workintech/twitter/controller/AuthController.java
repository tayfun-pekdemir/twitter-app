package com.workintech.twitter.controller;

import com.workintech.twitter.dto.*;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationService authenticationService,AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest request){
        User registeredUser = authenticationService.register(
                request.getFirstName(),
                request.getLastName(),
                request.getAccountName(),
                request.getEmail(),
                request.getPassword()
        );
        return new RegistrationResponse(
                registeredUser.getFirstName(),
                registeredUser.getLastName(),
                registeredUser.getAccountName(),
                registeredUser.getEmail()
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        User user = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return new LoginResponse(
                "Login successful for user: " + user.getUsername(),
                new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail())
        );
    }


}
