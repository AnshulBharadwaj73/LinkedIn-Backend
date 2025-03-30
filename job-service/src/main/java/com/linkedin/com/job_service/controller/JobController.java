package com.linkedin.com.job_service.controller;

import com.linkedin.com.job_service.Service.JobListingService;
import com.linkedin.com.job_service.dto.request.JobListingRequestDto;
import com.linkedin.com.job_service.dto.response.JobListingResponseDto;
import com.linkedin.com.job_service.entity.JobListing;
import com.linkedin.com.job_service.enums.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class JobController {

    private final JobListingService jobListingService;


    @PostMapping("/createJob")
    public ResponseEntity<JobListingResponseDto> createJob(@RequestBody JobListingRequestDto jobListingRequestDto){
        JobListingResponseDto jobListingResponseDto = jobListingService.jobCreate(jobListingRequestDto);

        return new ResponseEntity<>(jobListingResponseDto,HttpStatus.CREATED);
    }
    @GetMapping("/getByCountry/{location}")
    public ResponseEntity<List<JobListingResponseDto>> getJobsByLocation(@PathVariable String location) {
        Location jobLocation = Location.valueOf(location);  // Convert string to enum
        return new ResponseEntity<>(jobListingService.getJobByLocation(jobLocation),HttpStatus.FOUND);
    }

}
