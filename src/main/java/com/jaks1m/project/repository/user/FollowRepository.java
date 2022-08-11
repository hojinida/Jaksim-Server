package com.jaks1m.project.repository.user;

import com.jaks1m.project.domain.entity.follow.Follow;
import com.jaks1m.project.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser,User toUser);
}
