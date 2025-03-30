package com.linkedin.com.job_service.dto.response;


import com.linkedin.com.job_service.enums.JobType;
import com.linkedin.com.job_service.enums.Location;
import com.linkedin.com.job_service.enums.WorkplaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobListingResponseDto {

    private String jobTitle;

    private String company;

    private Long jobId;

    private WorkplaceType workplace;

    private Location location;

    private JobType jobType;

    private LocalDateTime jobCreated;
}
