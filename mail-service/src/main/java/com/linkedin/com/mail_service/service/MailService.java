package com.linkedin.com.mail_service.service;

import com.linkedin.com.mail_service.dto.MailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendMail(MailDto mailDto) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom("anshul.dub98@gmail.com");
        helper.setTo(mailDto.getTo());
        helper.setSubject(mailDto.getSubject());
        helper.setText(mailDto.getBody());
        File convFile = new File(Objects.requireNonNull(mailDto.getFile().getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(mailDto.getFile().getBytes());
        }

        FileSystemResource fileResource = new FileSystemResource(convFile);
        helper.addAttachment(mailDto.getFile().getOriginalFilename(),fileResource);

        javaMailSender.send(message);

        convFile.delete();
    }
}
