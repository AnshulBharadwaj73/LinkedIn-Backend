package com.linkedin.com.mail_service.controller;

import com.linkedin.com.mail_service.dto.MailDto;
import com.linkedin.com.mail_service.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping(value = "/send", consumes = "multipart/form-data")
    public ResponseEntity<String> mailSender(
            @RequestPart("to") String to,
            @RequestPart("subject") String subject,
            @RequestPart("body") String body,
            @RequestPart("file") MultipartFile file) throws MessagingException, IOException {

        MailDto mailDto = new MailDto(to, subject, body, file);
        mailService.sendMail(mailDto);

        return ResponseEntity.ok("Success");
    }
}
