package com.jaks1m.project.heart.application;

import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.board.domain.Board;
import com.jaks1m.project.heart.domain.Heart;
import com.jaks1m.project.notification.Notification;
import com.jaks1m.project.notification.domain.NotificationType;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.follow.domain.repository.BoardRepository;
import com.jaks1m.project.heart.domain.repository.HeartRepository;
import com.jaks1m.project.notification.domain.repository.NotificationRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    private final NotificationRepository notificationRepository;

    @Transactional
    public void addHeart(Long boardId){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));
        heartRepository.save(Heart.builder().board(board).user(user).build());
        notificationRepository.save(Notification.builder().user(board.getUser()).checked(false).notificationType(NotificationType.HEART)
                .valueId(board.getId()).message(user.getName().getName()+"님이 회원님의 게시물을 좋아합니다.").build());
    }

    @Transactional
    public void deleteHeart(Long boardId){
        Heart heart = checkUnauthorizedAccess(boardId);
        heartRepository.delete(heart);
    }

    private Heart checkUnauthorizedAccess(Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board= boardRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_BOARD));
        Heart heart = heartRepository.findByBoardAndUser(board,user)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HEART));
        if(heart.getUser()!=user){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return heart;
    }
}
