package com.jaks1m.project.follow.domain.repository;

import com.jaks1m.project.follow.domain.Follow;
import com.jaks1m.project.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser,User toUser);
}
