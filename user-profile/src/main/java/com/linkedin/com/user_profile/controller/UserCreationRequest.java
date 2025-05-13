package com.linkedin.com.user_profile.controller;

import com.linkedin.com.user_profile.dto.UserDto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserCreationRequest {
    private UserDto userDto;
    private MultipartFile image;
}
