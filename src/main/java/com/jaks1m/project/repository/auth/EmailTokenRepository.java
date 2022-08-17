package com.jaks1m.project.repository.auth;

import com.jaks1m.project.domain.entity.token.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken,Long> {// redis 사용가능
    Optional<EmailToken> findByToken(String emailToken);
    void deleteAllByExpirationDateAfter(LocalDateTime now);
}
