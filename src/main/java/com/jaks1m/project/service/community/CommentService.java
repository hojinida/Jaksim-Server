package com.jaks1m.project.service.community;

import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.community.Comment;
import com.jaks1m.project.domain.entity.notification.Notification;
import com.jaks1m.project.domain.entity.notification.NotificationType;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.dto.community.request.CommentAddRequestDto;
import com.jaks1m.project.repository.community.BoardRepository;
import com.jaks1m.project.repository.community.CommentRepository;
import com.jaks1m.project.repository.notification.NotificationRepository;
import com.jaks1m.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void addComment(CommentAddRequestDto request, Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_BOARD));
        commentRepository.save(Comment.builder().comment(request.getComment()).board(board).user(user).build());
        notificationRepository.save(Notification.builder().user(user).checked(false).notificationType(NotificationType.COMMENT)
                .value(board).message("회원님의 게시물의 댓글이 등록되었습니다.").build());
    }

    @Transactional
    public void editComment(CommentAddRequestDto request,Long commentId){
        Comment comment = checkUnauthorizedAccess(commentId);
        comment.updateComment(request.getComment());
    }

    @Transactional
    public void deleteComment(Long commentId){
        Comment comment = checkUnauthorizedAccess(commentId);
        commentRepository.delete(comment);
    }
    private Comment checkUnauthorizedAccess(Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Comment comment= commentRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_COMMENT));
        if(comment.getUser()!=user){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return comment;
    }
}
