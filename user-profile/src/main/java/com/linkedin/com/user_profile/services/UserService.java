package com.linkedin.com.user_profile.services;

import com.linkedin.com.user_profile.dto.UserDto;
import org.springframework.stereotype.Service;


public interface UserService {

    UserDto save(UserDto userDto);
}
