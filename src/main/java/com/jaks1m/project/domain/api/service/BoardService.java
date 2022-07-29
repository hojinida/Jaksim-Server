package com.jaks1m.project.domain.api.service;

import com.jaks1m.project.domain.api.dto.board.BoardPostRequestDto;
import com.jaks1m.project.domain.api.dto.board.BoardResponse;
import com.jaks1m.project.domain.api.entity.board.Board;
import com.jaks1m.project.domain.api.entity.board.BoardType;
import com.jaks1m.project.domain.api.entity.user.User;
import com.jaks1m.project.domain.api.repository.BoardRepository;
import com.jaks1m.project.domain.api.repository.UserRepository;
import com.jaks1m.project.domain.config.security.SecurityUtil;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postBoard(BoardPostRequestDto request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        boardRepository.save(Board.builder()
                .user(user)
                .boardType(request.getBoardType())
                .content(request.getContent())
                .title(request.getTitle())
                .countVisit(0L).build());
    }

    public List<BoardResponse> getList(Pageable pageable){
        List<BoardResponse> responses=new ArrayList<>();
        responses.addAll(getBoard(BoardType.FREE,pageable));
        responses.addAll(getBoard(BoardType.GROUP,pageable));
        responses.addAll(getBoard(BoardType.QNA,pageable));
        return responses;
    }
    public List<BoardResponse> getBoard(BoardType boardType, Pageable pageable){
        List<Board> boards=boardRepository.findAllByBoardTypeOrderByIdDesc(boardType,pageable);
        List<BoardResponse> response=new ArrayList<>();
        for(Board board:boards){
            response.add(BoardResponse.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .userName(board.getUser().getName().getName())
                    .visit(board.getCountVisit()).build());
        }
        return response;
    }
}
