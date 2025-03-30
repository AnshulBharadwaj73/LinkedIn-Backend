package com.linkedin.com.user_profile.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkExperienceDto {

    private String companyName;

    private String position;

    private Integer startMonth;

    private Integer startYear;

    private Integer endMonth;

    private Integer endYear;

    private String responsibility;

}
