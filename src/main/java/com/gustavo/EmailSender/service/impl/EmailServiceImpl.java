package com.gustavo.EmailSender.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gustavo.EmailSender.service.EmailService;
import com.gustavo.EmailSender.utils.EmailUtils;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.verify.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender emailSender;
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    public static final String UTF_8_ENCONDING = "UTF-8";

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(message);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());

        }
    }

    @Override
    @Async
    public void sendMimeMessageWithAttachment(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCONDING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));

            // Add attachments
            FileSystemResource wallpaper1 = new FileSystemResource(
                    new File(System.getProperty("user.home")
                            + "/images/1920x1080/artist_waves_colorful_129158_1920x1080.jpg"));

            FileSystemResource wallpaper2 = new FileSystemResource(
                    new File(System.getProperty("user.home")
                            + "/images/1920x1080/wallpaper11.jpg"));

            helper.addAttachment(wallpaper1.getFilename(), wallpaper1);
            helper.addAttachment(wallpaper2.getFilename(), wallpaper2);
            emailSender.send(message);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());

        }

    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
}
