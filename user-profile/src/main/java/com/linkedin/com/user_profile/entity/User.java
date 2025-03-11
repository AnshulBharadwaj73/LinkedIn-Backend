package com.linkedin.com.user_profile.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "profile")
public class User {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String pronouns;

    private String headline;

    private List<Education> educations;

    private List<WorkExperience> experiences;
//
//    private List<Education> educations;



}
