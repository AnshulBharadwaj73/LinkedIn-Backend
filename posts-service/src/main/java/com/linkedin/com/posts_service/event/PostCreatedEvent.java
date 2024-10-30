package com.linkedin.com.posts_service.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostCreatedEvent {

    Long creatorId;
    String content;
    Long postId;
}
