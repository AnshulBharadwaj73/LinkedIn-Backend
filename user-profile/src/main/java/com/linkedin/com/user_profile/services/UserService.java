package com.linkedin.com.user_profile.services;

import com.linkedin.com.user_profile.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {

//    UserDto save(UserDto userDto);

    UserDto save(UserDto userDto, MultipartFile file);

    UserDto update(UserDto userDto);

    UserDto patch(UserDto userDto);
}
