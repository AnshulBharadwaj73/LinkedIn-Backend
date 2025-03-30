package com.linkedin.com.user_profile.dto;


import com.linkedin.com.user_profile.entity.Education;
import com.linkedin.com.user_profile.entity.WorkExperience;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String id;

    private Long userId;

    private String firstName;

    private String lastName;

    private String pronouns;

    private String headline;

    private List<EducationDto> educations;

    private List<WorkExperienceDto> experiences;
}
