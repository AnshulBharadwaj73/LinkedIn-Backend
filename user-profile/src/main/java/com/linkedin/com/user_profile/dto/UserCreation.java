package com.linkedin.com.user_profile.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserCreation {

    private UserDto userDto;
    private MultipartFile image;
}
