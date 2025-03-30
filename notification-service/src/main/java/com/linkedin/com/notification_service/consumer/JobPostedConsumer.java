package com.linkedin.com.notification_service.consumer;

import com.linkedin.com.job_service.event.JobPostedEvent;
import com.linkedin.com.notification_service.clients.ConnectionsClient;
import com.linkedin.com.notification_service.clients.JobClient;
import com.linkedin.com.notification_service.dto.PersonDto;
import com.linkedin.com.notification_service.dto.request.JobListingRequestDto;
import com.linkedin.com.notification_service.dto.response.JobListingResponseDto;
import com.linkedin.com.posts_service.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PriorityQueue;


@Service
@RequiredArgsConstructor
@Slf4j
public class JobPostedConsumer {

    private final JobClient jobClient;

    @KafkaListener(topics = "job-posted-topic")
    public void handlePostCreated(JobPostedEvent jobPostedEvent){

        log.info("Sending notification : handlePostCreated {}",jobPostedEvent);

//        List<JobListingResponseDto> connections  =jobClient.createJob(JobListingRequestDto.builder().jobId(jobPostedEvent.getPostId()).jobTitle(jobPostedEvent.getName()).company(jobPostedEvent.getContent()).jobId(jobPostedEvent.getCreatorId()).build());

//        System.out.println(connections+"....................................");
//        for(PersonDto connection: connections){
//            sendNotification.send(connection.getUserId(),"Your Connection "+postCreatedEvent.getCreatorId()+" created a post.");
//        }


    }
}
