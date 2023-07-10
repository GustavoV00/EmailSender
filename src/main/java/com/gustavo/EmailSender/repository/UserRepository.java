package com.gustavo.EmailSender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.EmailSender.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);
}
