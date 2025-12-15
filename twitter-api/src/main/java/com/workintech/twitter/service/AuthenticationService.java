package com.workintech.twitter.service;

import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.DuplicateException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.RoleRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String firstName,String lastName,String username,String email,String password){

        if(userRepository.findByUsername(username).isPresent()){
            throw new DuplicateException("Username already exists");
        }

        if(userRepository.findByEmail(email).isPresent()){
            throw new DuplicateException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow(
                () -> new NotFoundException("Role USER not found")
        );
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(encodedPassword);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setAuthorities(roles);
        return userRepository.save(user);
    }
}
