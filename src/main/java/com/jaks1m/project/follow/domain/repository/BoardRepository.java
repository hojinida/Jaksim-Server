package com.jaks1m.project.follow.domain.repository;

import com.jaks1m.project.board.domain.Board;
import com.jaks1m.project.board.domain.BoardType;
import com.jaks1m.project.user.domain.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByBoardType(BoardType boardType,Pageable pageable);
    List<Board> findAllByStatusAndLastModifiedDateAfter(Status status, LocalDateTime localDateTime);
}
