package com.linkedin.com.notification_service.clients;


import com.linkedin.com.notification_service.dto.request.JobListingRequestDto;
import com.linkedin.com.notification_service.dto.response.JobListingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "job-service", path = "/jobs")
public interface JobClient {

    @PostMapping("/core/createJob")
    public List<JobListingResponseDto> createJob(@RequestBody JobListingRequestDto jobListingRequestDto);

}
