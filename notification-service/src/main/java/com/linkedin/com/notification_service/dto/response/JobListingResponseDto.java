package com.linkedin.com.notification_service.dto.response;


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

    private LocalDateTime jobCreated;
}
