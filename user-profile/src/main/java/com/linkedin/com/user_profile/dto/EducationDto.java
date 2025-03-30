package com.linkedin.com.user_profile.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationDto {

    private String instituteName;

    private String degree;

    private Integer startMonth;

    private Integer startYear;
}
