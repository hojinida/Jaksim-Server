package com.jaks1m.project.domain.api.service;

import com.jaks1m.project.domain.api.dto.board.BoardPostRequestDto;
import com.jaks1m.project.domain.api.dto.board.BoardResponse;
import com.jaks1m.project.domain.api.entity.board.Board;
import com.jaks1m.project.domain.api.entity.board.BoardType;
import com.jaks1m.project.domain.api.entity.user.Status;
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
import java.util.Optional;

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

    @Transactional
    public BoardResponse getBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        board.get().updateVisit();
        return boardResponseBuilder(board.get());
    }
    @Transactional
    public BoardResponse editBoard(BoardPostRequestDto request,Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        board.get().updateBoard(request.getTitle(), request.getContent(), request.getBoardType());

        return boardResponseBuilder(board.get());
    }

    @Transactional
    public void deleteBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        board.get().updateStatus(Status.DELETE);
    }

    public List<BoardResponse> getBoardList(Pageable pageable){
        List<BoardResponse> responses=new ArrayList<>();
        responses.addAll(getBoards(BoardType.FREE,pageable));
        responses.addAll(getBoards(BoardType.GROUP,pageable));
        responses.addAll(getBoards(BoardType.QNA,pageable));
        return responses;
    }
    public List<BoardResponse> getBoards(BoardType boardType, Pageable pageable){
        List<Board> boards=boardRepository.findAllByBoardTypeOrderByIdDesc(boardType,pageable);
        List<BoardResponse> response=new ArrayList<>();
        for(Board board:boards){
            response.add(boardResponseBuilder(board));
        }
        return response;
    }

    private BoardResponse boardResponseBuilder(Board board){
        return BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .userName(board.getUser().getName().getName())
                .createdData(board.getCreatedData())
                .lastModifiedDate(board.getLastModifiedDate())
                .visit(board.getCountVisit()).build();
    }
}
