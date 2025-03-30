package com.linkedin.com.user_profile.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Education {

    private String instituteName;

    private String degree;

    private Integer startMonth;

    private Integer startYear;
}
