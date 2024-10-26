package com.linkedin.com.posts_service.controller;

import com.linkedin.com.posts_service.auth.UserContextHolder;
import com.linkedin.com.posts_service.dto.PostCreateRequestDto;
import com.linkedin.com.posts_service.dto.PostDto;
import com.linkedin.com.posts_service.entity.Post;
import com.linkedin.com.posts_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto, HttpServletRequest httpServletRequest){
        PostDto createdPost = postService.createPost(postDto,1L);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId){
        Long userId = UserContextHolder.getCurrentUserId();
//        String userId=httpServletRequest.getHeader("X-User-Id");
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.FOUND).body(postDto);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId){
        List<PostDto> posts = postService.getAllPostsOfUser(userId);

        return ResponseEntity.ok(posts);
    }
}