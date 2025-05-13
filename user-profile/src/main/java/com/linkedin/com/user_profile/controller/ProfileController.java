package com.linkedin.com.user_profile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.com.user_profile.dto.UserCreation;
import com.linkedin.com.user_profile.dto.UserDto;
import com.linkedin.com.user_profile.entity.User;
import com.linkedin.com.user_profile.repository.UserRepository;
import com.linkedin.com.user_profile.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/core")
//@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;


    public ProfileController(UserService userService) {
        this.userService = userService;

    }


    @GetMapping
    public String use(){
        return "Hello";
    }

    @PostMapping(value = "/user",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDto addUser(
            @RequestPart("userDto") String userDtoJson,
            @RequestPart(value = "image") MultipartFile file
    ) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto=objectMapper.readValue(userDtoJson,UserDto.class);
        return userService.save(userDto,file);
    }


    @PutMapping("/update")
    public UserDto updateUser(@RequestBody UserDto userDto){

        return userService.update(userDto);
    }

    @PatchMapping("/patch")
    public UserDto patchUser(@RequestBody UserDto userDto){

        return userService.update(userDto);
    }
}
