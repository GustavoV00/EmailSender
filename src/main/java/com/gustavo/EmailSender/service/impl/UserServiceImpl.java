package com.gustavo.EmailSender.service.impl;

import org.springframework.stereotype.Service;

import com.gustavo.EmailSender.domain.Confirmation;
import com.gustavo.EmailSender.domain.User;
import com.gustavo.EmailSender.repository.ConfirmationRepository;
import com.gustavo.EmailSender.repository.UserRepository;
import com.gustavo.EmailSender.service.EmailService;
import com.gustavo.EmailSender.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setEnabled(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

        // Send email to user with token
        // emailService.sendSimpleMailMessage(user.getName(), user.getEmail(),
        // confirmation.getToken());
        emailService.sendMimeMessageWithAttachment(user.getName(), user.getEmail(), confirmation.getToken());

        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        return Boolean.TRUE;
    }

}
