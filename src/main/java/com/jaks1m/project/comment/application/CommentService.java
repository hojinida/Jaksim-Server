package com.jaks1m.project.comment.application;

import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.board.domain.Board;
import com.jaks1m.project.comment.domain.Comment;
import com.jaks1m.project.notification.Notification;
import com.jaks1m.project.notification.domain.NotificationType;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.comment.presentation.dto.CommentCreateRequest;
import com.jaks1m.project.follow.domain.repository.BoardRepository;
import com.jaks1m.project.comment.domain.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void addComment(CommentCreateRequest request, Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_BOARD));
        commentRepository.save(Comment.builder().comment(request.getComment()).board(board).user(user).build());
        notificationRepository.save(Notification.builder().user(user).checked(false).notificationType(NotificationType.COMMENT)
                .valueId(board.getId()).message("회원님의 게시물의 댓글이 등록되었습니다.").build());
    }

    @Transactional
    public void editComment(CommentCreateRequest request, Long commentId){
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
