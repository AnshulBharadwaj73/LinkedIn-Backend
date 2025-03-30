package com.linkedin.com.job_service.Service;

import com.linkedin.com.job_service.dto.request.JobListingRequestDto;
import com.linkedin.com.job_service.dto.response.JobListingResponseDto;
import com.linkedin.com.job_service.entity.JobListing;
import com.linkedin.com.job_service.enums.Location;
import com.linkedin.com.job_service.event.JobPostedEvent;
import com.linkedin.com.job_service.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobListingService {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, JobPostedEvent> kafkaTemplate;

//    public JobListingService(JobRepository jobRepository) {
//        this.jobRepository = jobRepository;
//    }

    public JobListingResponseDto jobCreate(JobListingRequestDto jobListingRequestDto){
        JobListing jobListing = new JobListing();

        jobListing.setJobTitle(jobListingRequestDto.getJobTitle());
        jobListing.setCompany(jobListingRequestDto.getCompany());
        jobListing.setJobId(jobListingRequestDto.getJobId());
        jobListing.setJobType(jobListingRequestDto.getJobType());
        jobListing.setLocation(jobListingRequestDto.getLocation());
        jobListing.setWorkplace(jobListingRequestDto.getWorkplace());

        JobListing job = jobRepository.save(jobListing);
        JobListingResponseDto jobListingResponseDto =  modelMapper.map(job, JobListingResponseDto.class);
//        System.out.println(jobListingResponseDto);

        JobPostedEvent jobPostedEvent = JobPostedEvent.builder().postId(job.getId()).name(job.getCompany()).postId(job.getJobId()).content(job.getJobTitle()).creatorId(jobListing.getJobId()).build();

        kafkaTemplate.send("job-posted-topic",jobPostedEvent);

        return jobListingResponseDto;
    }

    public List<JobListingResponseDto> getJobByLocation(Location country){
        List<JobListing> list=jobRepository.findJobsByLocation(country);
        return list.stream()
                .map(job -> modelMapper.map(job, JobListingResponseDto.class))
                .collect(Collectors.toList());
    }
}
