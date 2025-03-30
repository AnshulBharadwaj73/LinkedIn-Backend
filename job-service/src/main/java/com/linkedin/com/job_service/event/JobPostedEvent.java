package com.linkedin.com.job_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobPostedEvent {

    Long creatorId;
    String content;
    String name;
    Long postId;
}
