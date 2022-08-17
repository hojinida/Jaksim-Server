package com.jaks1m.project.service.community;

import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.community.Heart;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.repository.community.BoardRepository;
import com.jaks1m.project.repository.community.HeartRepository;
import com.jaks1m.project.repository.user.UserRepository;
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

    @Transactional
    public void addHeart(Long boardId){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));
        heartRepository.save(Heart.builder().board(board).user(user).build());
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
