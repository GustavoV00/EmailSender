package com.gustavo.EmailSender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.EmailSender.domain.Confirmation;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByToken(String token);
}
