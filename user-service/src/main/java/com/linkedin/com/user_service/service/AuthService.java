package com.linkedin.com.user_service.service;

import com.linkedin.com.user_service.clients.ConnectionsClient;
import com.linkedin.com.user_service.dto.*;
import com.linkedin.com.user_service.entity.User;
import com.linkedin.com.user_service.exception.BadRequestException;
import com.linkedin.com.user_service.exception.ResourceNotFoundException;
import com.linkedin.com.user_service.repository.UserRepository;
import com.linkedin.com.user_service.util.PasswordUtil;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Marker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final ConnectionsClient connectionsClient;
//    private final AuthenticationManager authenticationManager;

    public UserDto signup(SignupRequestDto signupRequestDto) {
        log.info("Signing up with the User {}", signupRequestDto);
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) {
            log.error("User already exists, cannot signup again {}", signupRequestDto.getName());
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser= userRepository.save(user);
        PersonDto personDto = modelMapper.map(savedUser, PersonDto.class);
        log.info("User Created with {}",personDto);

        Boolean userCreatedAtConnectionService = connectionsClient.addUserToConnectionService(personDto);
        if(!userCreatedAtConnectionService ){
            log .info("User cannot be created at Connection service");
            throw new ResourceNotFoundException("User cannot be created at Connection service");

        }
        log.info("User Created at connection Service ");

        return modelMapper.map(user,UserDto.class);
    }


    public String login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()-> new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(),user.getPassword());

        if(!isPasswordMatch){
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }

    public Boolean deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with "+userId+" not found"));

        userRepository.delete(user);
        connectionsClient.deleteUserToConnectionService(userId);

        return true;
    }

//    public Long getUserId(){
//        return
//    }
}
