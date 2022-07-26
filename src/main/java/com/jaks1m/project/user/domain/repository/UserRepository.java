package com.jaks1m.project.user.domain.repository;

import com.jaks1m.project.user.domain.Status;
import com.jaks1m.project.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByStatusAndLastModifiedDateAfter(Status status, LocalDateTime localDateTime);
}
