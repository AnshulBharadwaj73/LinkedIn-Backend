package com.linkedin.com.posts_service.service;

import com.linkedin.com.posts_service.auth.UserContextHolder;
import com.linkedin.com.posts_service.entity.Post;
import com.linkedin.com.posts_service.entity.PostLike;
import com.linkedin.com.posts_service.event.PostLikedEvent;
import com.linkedin.com.posts_service.exception.BadRequestException;
import com.linkedin.com.posts_service.exception.ResourceNotFoundException;
import com.linkedin.com.posts_service.repository.PostLikeRepository;
import com.linkedin.com.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    private final KafkaTemplate<Long, PostLikedEvent> kafkaTemplate;


    public void likePost(Long postId){

        log.info("Attempting to like the post with id: {}",postId);

        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id "+postId));
        boolean exits = postRepository.existsById(postId);
        if(!exits) throw new ResourceNotFoundException("Post not found with id "+postId);
        Long userId = UserContextHolder.getCurrentUserId();

//        boolean alreadyLike = postLikeRepository.existsByUserIdAndPostId(userId,postId);
//        if(alreadyLike) throw new BadRequestException("Cannot like the same post");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);

        postLikeRepository.save(postLike);
        log.info("Post with id: {} liked successfully",postId);

        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                .postId(postId)
                .likedByUserId(userId)
                .creatorId(post.getUserId())
                .build();

        kafkaTemplate.send("post-liked-topic",postId, postLikedEvent);
    }

    public void unLikePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to unlike the post with id: {}",postId);
        boolean exits = postRepository.existsById(postId);
        if(!exits) throw new ResourceNotFoundException("Post not found with id "+postId);

        boolean alreadyLike = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!alreadyLike) throw new BadRequestException("Cannot like the post which is not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId,postId);
        log.info("Post with id: {} unliked successfully",postId);
    }
}
