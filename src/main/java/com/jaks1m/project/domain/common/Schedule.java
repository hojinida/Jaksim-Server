package com.jaks1m.project.domain.common;

import com.jaks1m.project.domain.api.entity.board.Board;
import com.jaks1m.project.domain.api.entity.user.Status;
import com.jaks1m.project.domain.api.entity.user.User;
import com.jaks1m.project.domain.api.repository.BoardRepository;
import com.jaks1m.project.domain.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Schedule {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    @Scheduled(cron = "0 0 18 L * ?")//매월 말일 18시 0분에 실행
    public void deleteUser(){
        log.info("Schedule User Delete Run");
        List<User> users = userRepository.findAllByStatusAndLastModifiedDateAfter(Status.DELETE, LocalDateTime.now().minusYears(5));
        userRepository.deleteAll(users);
        log.info("Schedule Board Delete Run");
        List<Board> boards=boardRepository.findAllByStatusAndLastModifiedDateAfter(Status.DELETE,LocalDateTime.now().minusYears(5));
        boardRepository.deleteAll(boards);
    }
}
