package com.jaks1m.project.domain.api.repository;

import com.jaks1m.project.domain.api.entity.board.Board;
import com.jaks1m.project.domain.api.entity.board.BoardType;
import com.jaks1m.project.domain.api.entity.user.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByBoardTypeOrderByIdDesc(BoardType boardType,Pageable pageable);
    List<Board> findAllByStatusAndLastModifiedDateBefore(Status status, LocalDateTime localDateTime);
}
