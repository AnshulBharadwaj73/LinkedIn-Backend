package com.linkedin.com.job_service.entity;

import com.linkedin.com.job_service.enums.JobType;
import com.linkedin.com.job_service.enums.WorkplaceType;
import com.linkedin.com.job_service.enums.Location;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "job_listing")
@NoArgsConstructor
@AllArgsConstructor
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;

    private String company;

    private Long jobId;

    @Enumerated(EnumType.STRING)
    private WorkplaceType workplace;

    @Enumerated(EnumType.STRING)
    private Location location;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @CreationTimestamp
    private LocalDateTime jobCreated;
}
