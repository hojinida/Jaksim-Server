package com.jaks1m.project.repository.community;

import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.community.Heart;
import com.jaks1m.project.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long> {
    Optional<Heart> findByBoardAndUser(Board board, User user);
}
