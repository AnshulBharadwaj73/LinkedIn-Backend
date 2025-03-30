package com.linkedin.com.posts_service.service;

import com.linkedin.com.posts_service.auth.UserContextHolder;
import com.linkedin.com.posts_service.dto.PostCreateRequestDto;
import com.linkedin.com.posts_service.dto.PostDto;
import com.linkedin.com.posts_service.entity.Post;
import com.linkedin.com.posts_service.event.PostCreatedEvent;
import com.linkedin.com.posts_service.exception.ResourceNotFoundException;
import com.linkedin.com.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionPostService connectionPostService;

    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;

    public PostDto createPost(PostCreateRequestDto postDto) {
        Long userId = UserContextHolder.getCurrentUserId();
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postRepository.save(post);

        PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                .postId(savedPost.getId())
                .creatorId(userId)
                .content(savedPost.getContent())
                .build();

        kafkaTemplate.send("post-created-topic",postCreatedEvent);
        return modelMapper.map(savedPost, PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        log.debug("Retrieving post with ID: {}", postId);
//        Long userId = UserContextHolder.getCurrentUserId();
//
//        List<PersonDto> firstConnection = connectionsClient.getFirstConnections();
//
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found with id "+postId));

        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);

        return posts.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
    }

    public List<Post> getAllPosts(){
        List<Post> posts = postRepository.findAll();

        List<Long> connectedUserIds=connectionPostService.getAllConnection().stream().map(id -> id.getUserId()).toList();

        List<Post> post = connectedUserIds.stream()
                .flatMap(id -> postRepository.findByUserId(id).stream()) // Fetch posts for each user
                .toList();

        return post;
    }

}
