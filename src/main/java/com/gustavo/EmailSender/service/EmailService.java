package com.gustavo.EmailSender.service;

public interface EmailService {

    void sendSimpleMailMessage(String name, String to, String token);

    void sendMimeMessageWithAttachment(String name, String to, String token);

}
