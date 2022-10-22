package com.jaks1m.project.heart.domain.repository;

import com.jaks1m.project.board.domain.Board;
import com.jaks1m.project.heart.domain.Heart;
import com.jaks1m.project.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long> {
    Optional<Heart> findByBoardAndUser(Board board, User user);
}
