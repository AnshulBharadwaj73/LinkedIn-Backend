package com.linkedin.com.mail_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

//    private String from;
    private String to;
    private String subject;
    private String body;
    private MultipartFile file;

}
