package com.workintech.twitter.service;

import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.DuplicateException;
import com.workintech.twitter.repository.RoleRepository;
import com.workintech.twitter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    AuthenticationService authenticationService;

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        authenticationService =
                new AuthenticationService(userRepository, roleRepository, passwordEncoder);
    }

    @DisplayName("Can register user successfully")
    @Test
    void register() {

        when(userRepository.findByUserName("userName"))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());

        when(roleRepository.findByAuthority("USER"))
                .thenReturn(Optional.of(new Role(1L, "USER")));

        User savedUser = new User();
        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User result = authenticationService.register(
                "Test",
                "User",
                "userName",
                "test@example.com",
                "123456"
        );

        assertEquals(savedUser, result);
        verify(userRepository).save(any(User.class));
    }

    @DisplayName("Can throw exception for duplicate registration")
    @Test
    void duplicateRegister() {

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new User()));

        assertThrows(DuplicateException.class, () ->
                authenticationService.register(
                        "Test",
                        "User",
                        "userName",
                        "test@example.com",
                        "123456"
                )
        );
    }
    @DisplayName("Can login user successful")
    @Test
    void login() {

        User mockUser = new User();
        mockUser.setEmail("testuser@example.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("testuser@example.com"))
                .thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("123456", "encodedPassword"))
                .thenReturn(true);

        User result =
                authenticationService.login("testuser@example.com", "123456");

        assertEquals(mockUser, result);
    }
}
