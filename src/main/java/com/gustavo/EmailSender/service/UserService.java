package com.gustavo.EmailSender.service;

import com.gustavo.EmailSender.domain.User;

public interface UserService {
    User saveUser(User user);

    Boolean verifyToken(String token);
}
