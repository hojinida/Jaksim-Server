package com.jaks1m.project.repository.user;

import com.jaks1m.project.domain.entity.user.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends,Long> {
    Optional<Friends> findByFriendId(Long friendId);
}
