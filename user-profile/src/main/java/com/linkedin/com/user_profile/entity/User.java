package com.linkedin.com.user_profile.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.web.multipart.MultipartFile;

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
    private String id;

    private Long userId;

    private String profilePicture;

    private String firstName;

    private String lastName;

    private String pronouns;

    private String headline;

    private List<Education> educations;

    private List<WorkExperience> experiences;
//
//    private List<Education> educations;



}
