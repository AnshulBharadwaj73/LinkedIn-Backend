package com.linkedin.com.user_profile.services;

import com.linkedin.com.user_profile.auth.UserContextHolder;
import com.linkedin.com.user_profile.client.UploaderClient;
import com.linkedin.com.user_profile.dto.EducationDto;
import com.linkedin.com.user_profile.dto.UserDto;
import com.linkedin.com.user_profile.entity.Education;
import com.linkedin.com.user_profile.entity.User;
import com.linkedin.com.user_profile.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UploaderClient uploaderService;


    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper, UploaderClient uploaderService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.uploaderService = uploaderService;
    }

    @Override
    public UserDto save(UserDto userDto, MultipartFile file) {
        User userDto1= modelMapper.map(userDto, User.class);
        System.out.println("UserDto: "+userDto1);
        // Set the userId from UserContextHolder
        Long userId = UserContextHolder.getCurrentUserId();
        System.out.println("User ID from context: " + userId);
//        if (userId == null) {
//            throw new RuntimeException("User ID not found in context");
//        }
//        // Set the userId in the UserDto
        userDto1.setUserId(7L);
        // Upload the image and set the URL in the UserDto
        String imageUrl = (uploaderService.uploadImage(file));
        userDto1.setProfilePicture(imageUrl);
        User savedUser = userRepository.insert(userDto1);

        // Convert back to DTO before returning
        return modelMapper.map(savedUser, UserDto.class);
    }

//    @Override
//    public UserDto save(UserDto userDto) {
//        return null;
//    }

    @Override
    public UserDto update(UserDto userDto) {
        User user= userRepository.findByFirstName(userDto.getFirstName());
        if(user == null){
            throw new RuntimeException("user not found in mongo");
        }
        User savedUser = userRepository.save(modelMapper.map(userDto,User.class));

        return modelMapper.map(savedUser,UserDto.class);

    }

    @Override
    public UserDto patch(UserDto userDto) {
        if(userDto.getId() != null){
            User existUser = userRepository.findByFirstName(userDto.getFirstName());
            ArrayList<EducationDto> list=new ArrayList<>(userDto.getEducations());
            ArrayList<Education> list2=new ArrayList<>(existUser.getEducations());
            boolean change = list2.stream()
                    .anyMatch(li -> list.stream()
                            .noneMatch(lis -> li.getDegree().length() == lis.getDegree().length()) // Compare lengths
                    );
            if (change) {
                existUser.setEducations(userDto.getEducations().stream()
                        .map(eduDto -> modelMapper.map(eduDto, Education.class)) // Convert DTO to Entity
                        .collect(Collectors.toList()) // Collect as List
                );
            }
            return modelMapper.map(userRepository.save(existUser), UserDto.class);
        }
        return null;
    }

}
