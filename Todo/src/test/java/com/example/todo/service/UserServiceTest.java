package com.example.todo.service;

import com.example.todo.dto.UserRequestDto;
import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenNewUser() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // When
        userService.signup(userRequestDto);

        // Then
        verify(userRepository, times(1)).findByUsername("newUser");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void givenExistingUser() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        // When
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.signup(userRequestDto));
    }

    @Test
    void givenExistingUserAndPassword() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        User existingUser = new User("existingUser", "encodedPassword");
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // When
        userService.login(userRequestDto);
        // Then
        verify(userRepository, times(1)).findByUsername("existingUser");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
    }

    @Test
    void givenNonExistingUser() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        // When
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(userRequestDto));
    }

    @Test
    void givenExistingUserAndWrongPassword() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        User existingUser = new User("existingUser", "encodedPassword");
        // When
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(userRequestDto));
    }
}
