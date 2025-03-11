package com.linkedin.com.user_profile.services;

import com.linkedin.com.user_profile.auth.UserContextHolder;
import com.linkedin.com.user_profile.dto.UserDto;
import com.linkedin.com.user_profile.entity.User;
import com.linkedin.com.user_profile.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final HttpClient httpClient=HttpClient.newHttpClient();

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User userDto1= modelMapper.map(userDto, User.class);
        Long userId = UserContextHolder.getCurrentUserId();
        userDto1.setId(userId);
        userRepository.save(userDto1);
        return userDto;
    }
}
