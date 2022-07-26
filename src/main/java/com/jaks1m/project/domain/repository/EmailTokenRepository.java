package com.jaks1m.project.domain.repository;

import com.jaks1m.project.domain.model.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken,Long> {// redis 사용가능
    Optional<EmailToken> findByEmailTokenAndExpirationDateAfterAndExpired(String emailToken,LocalDateTime now,Boolean expired);
}
