package com.linkedin.com.user_profile.controller;

import com.linkedin.com.user_profile.dto.UserDto;
import com.linkedin.com.user_profile.entity.User;
import com.linkedin.com.user_profile.repository.UserRepository;
import com.linkedin.com.user_profile.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user")
    public UserDto addUser(@RequestBody UserDto userDto){

        return userService.save(userDto);
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
