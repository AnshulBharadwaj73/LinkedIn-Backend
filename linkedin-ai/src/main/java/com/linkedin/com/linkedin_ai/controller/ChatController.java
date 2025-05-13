package com.linkedin.com.linkedin_ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @PostMapping("/chat-with-image")
    public String chat(@RequestPart("message") String message, @RequestPart("image") MultipartFile file){
        return chatClient.prompt()
                .user(prompt -> {
                    prompt.text(message)
                            .media(MimeTypeUtils.IMAGE_PNG, new InputStreamResource(file));
                })
                .call()
                .content();
    }

    @GetMapping(value = "/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestParam("message") String message){
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .doOnNext(System.out::println);
    }
}
