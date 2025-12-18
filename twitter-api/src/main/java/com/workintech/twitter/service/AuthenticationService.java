package com.workintech.twitter.service;

import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.DuplicateException;
import com.workintech.twitter.exception.NotAllowedException;
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

    public User register(String firstName,String lastName,String accountName,String email,String password){

        if(userRepository.findByAccountName(accountName).isPresent()){
            throw new DuplicateException("Account name already exists");
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
        user.setAccountName(accountName);
        user.setPassword(encodedPassword);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setAuthorities(roles);
        return userRepository.save(user);
    }

    public User login(String email,String password){
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found by email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotAllowedException("Invalid email or password");
        }
        return user;
    }
}
