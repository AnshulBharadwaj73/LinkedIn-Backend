package com.linkedin.com.user_service.service;

import com.linkedin.com.user_service.dto.SignupRequestDto;
import com.linkedin.com.user_service.dto.UserDto;
import com.linkedin.com.user_service.entity.User;
import com.linkedin.com.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    private final String TEST_EMAIL = "test@example.com";
    private final String ENCODED_PASSWORD = "$2a$12$hashedPassword";


    @BeforeEach
    void setUp() {
        // Initialize mocks and any other setup needed before each test
        user = User.builder()
                .id(1L)
                .email(TEST_EMAIL)
                .password(ENCODED_PASSWORD)
                .name("Anshul Bharadwaj")
                .build();
    }


    @Test
    @DisplayName("Load user by email")
    void getUserById_whenUserExists_returnsUser() {
    // Arrange
//        user = User.builder()
//                .id(99L)
//                .name("Anshul Bharadwaj")
//                .email("test@example.com")
//                .password("$2a$12$hashedPassword")
//                .build();
            when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(user.getId());

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getUserByEmail_whenEmailExists_returnUser(){
        // Arrange
        when(userRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByEmail(TEST_EMAIL);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_EMAIL, result.getEmail());
        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Save User")
    void signUp_whenEmailNotExists_savesUser() {
        // Arrange
        SignupRequestDto signupRequestDto=new SignupRequestDto();
        signupRequestDto.setEmail(TEST_EMAIL);
        signupRequestDto.setPassword("password");
        signupRequestDto.setName("Anshul Bharadwaj");

        UserDto expectedDto = new UserDto();
        expectedDto.setId(1L);
        expectedDto.setEmail(TEST_EMAIL);
        expectedDto.setName("Anshul Bharadwaj");



        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(modelMapper.map(signupRequestDto, User.class)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, SignupRequestDto.class)).thenReturn(signupRequestDto);
        when(modelMapper.map(user, UserDto.class)).thenReturn(expectedDto);

        // Act
        UserDto result = userService.signUp(signupRequestDto);

        // Assert
        assertEquals(expectedDto, result);
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(passwordEncoder).encode(ENCODED_PASSWORD);
        verify(userRepository).save(user);

    }
}