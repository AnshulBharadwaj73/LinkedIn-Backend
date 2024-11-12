package com.linkedin.com.user_service.controller;

import com.linkedin.com.user_service.dto.DeleteUserDto;
import com.linkedin.com.user_service.dto.LoginRequestDto;
import com.linkedin.com.user_service.dto.SignupRequestDto;
import com.linkedin.com.user_service.dto.UserDto;
import com.linkedin.com.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto){
        UserDto userDto =authService.signup(signupRequestDto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        String token =authService.login(loginRequestDto);

        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long userId){
        return ResponseEntity.ok(authService.deleteUser(userId));
    }
}
