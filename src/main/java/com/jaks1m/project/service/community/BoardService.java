package com.jaks1m.project.service.community;

import com.jaks1m.project.dto.community.request.BoardPostRequestDto;
import com.jaks1m.project.dto.community.response.BoardResponse;
import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.community.BoardType;
import com.jaks1m.project.domain.entity.user.Status;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.repository.func.BoardRepository;
import com.jaks1m.project.repository.user.UserRepository;
import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.service.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final AwsS3Service awsS3Service;

    @Transactional
    public void addBoard(List<MultipartFile> multipartFiles, BoardPostRequestDto request) throws IOException {
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board=Board.builder().user(user).boardType(request.getBoardType()).content(request.getContent())
                .title(request.getTitle()).countVisit(0L).build();
        awsS3Service.upload(multipartFiles,"upload",board);
        boardRepository.save(board);
    }

    @Transactional
    public BoardResponse getBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        board.get().updateVisit();
        return board.get().toBoardResponse();
    }
    @Transactional
    public BoardResponse editBoard(List<MultipartFile> multipartFiles,BoardPostRequestDto request,Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        board.get().updateBoard(request.getTitle(), request.getContent(), request.getBoardType());
        awsS3Service.remove(board.get());
        return board.get().toBoardResponse();
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
            response.add(board.toBoardResponse());
        }
        return response;
    }
}
