package com.linkedin.com.job_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostedEvent {

    Long creatorId;
    String content;
    String name;
    Long postId;
}
