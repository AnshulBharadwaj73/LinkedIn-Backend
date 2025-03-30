package com.linkedin.com.notification_service.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobListingRequestDto {

    private String jobTitle;

    private String company;

    private Long jobId;

}
