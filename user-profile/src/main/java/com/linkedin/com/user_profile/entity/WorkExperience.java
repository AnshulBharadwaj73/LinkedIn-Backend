package com.linkedin.com.user_profile.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "workexperience")
public class WorkExperience {

    private String companyName;

    private String position;

    private Integer startMonth;

    private Integer startYear;

    private Integer endMonth;

    private Integer endYear;

    private String responsibility;

}
