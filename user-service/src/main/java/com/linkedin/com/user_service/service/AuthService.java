package com.linkedin.com.user_service.service;

import com.linkedin.com.user_service.clients.ConnectionsClient;
import com.linkedin.com.user_service.dto.LoginRequestDto;
import com.linkedin.com.user_service.dto.PersonDto;
import com.linkedin.com.user_service.dto.SignupRequestDto;
import com.linkedin.com.user_service.dto.UserDto;
import com.linkedin.com.user_service.entity.User;
import com.linkedin.com.user_service.exception.BadRequestException;
import com.linkedin.com.user_service.exception.ResourceNotFoundException;
import com.linkedin.com.user_service.repository.UserRepository;
import com.linkedin.com.user_service.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final ConnectionsClient connectionsClient;

    public UserDto signup(SignupRequestDto signupRequestDto) {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser= userRepository.save(user);
        PersonDto personDto = modelMapper.map(savedUser, PersonDto.class);
        System.out.println(personDto);
        connectionsClient.addUserToConnectionService(personDto);

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
}
