package com.jaks1m.project.board.application;

import com.jaks1m.project.aws.domain.S3Image;
import com.jaks1m.project.board.presentation.dto.BoardCreateRequest;
import com.jaks1m.project.board.application.dto.BoardResponse;
import com.jaks1m.project.board.domain.Board;
import com.jaks1m.project.board.domain.BoardType;
import com.jaks1m.project.user.domain.Status;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.aws.application.dto.ImageDto;
import com.jaks1m.project.follow.domain.repository.BoardRepository;
import com.jaks1m.project.aws.domain.repository.S3ImageRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
    private final S3ImageRepository s3ImageRepository;
    @Transactional
    public void addBoard(List<ImageDto> imageDto, BoardCreateRequest request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board=Board.builder().user(user).boardType(request.getBoardType()).content(request.getContent())
                .title(request.getTitle()).bracket(request.getBracket()).countVisit(0L).build();
        boardRepository.save(board);
        saveS3Image(imageDto,board);
    }
    @Transactional
    public BoardResponse getBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        board.get().updateVisit();
        return createBoardResponse(board.get());
    }
    @Transactional
    public BoardResponse editBoard(List<ImageDto> imageDto, BoardCreateRequest request, Long id){
        Board board = checkUnauthorizedAccess(id);
        board.updateBoard(request.getTitle(), request.getBracket(),request.getContent(), request.getBoardType());
        s3ImageRepository.deleteAll(board.getS3Images());
        saveS3Image(imageDto,board);

        return createBoardResponse(board);
    }

    @Transactional
    public void deleteBoard(Long id){
        Board board=checkUnauthorizedAccess(id);
        board.updateStatus(Status.DELETE);
    }

    public List<BoardResponse> getBoardList(Pageable pageable){
        List<BoardResponse> responses=new ArrayList<>();
        responses.addAll(getBoards(BoardType.NOTICE,pageable));
        responses.addAll(getBoards(BoardType.FREE,pageable));
        responses.addAll(getBoards(BoardType.FRIEND,pageable));
        return responses;
    }
    public List<BoardResponse> getBoards(BoardType boardType, Pageable pageable){
        PageRequest pageRequest=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),pageable.getSort());
        List<Board> boards=boardRepository.findByBoardType(boardType,pageRequest);
        List<BoardResponse> response=new ArrayList<>();
        for(Board board:boards){
            response.add(createBoardResponse(board));
        }
        return response;
    }

    private void saveS3Image(List<ImageDto> imageDto,Board board){
        if(imageDto!=null) {
            List<S3Image> s3Images = new ArrayList<>();
            for (ImageDto image : imageDto) {
                s3Images.add(S3Image.builder().imageKey(image.getKey()).imagePath(image.getPath()).board(board).build());
            }
            s3ImageRepository.saveAll(s3Images);
        }
    }

    private BoardResponse createBoardResponse(Board board){
        return BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .bracket(board.getBracket())
                .content(board.getContent())
                .userName(board.getUser().getName().getName())
                .visits(board.getCountVisit())
                .hearts((long) board.getHearts().size())
                .images(board.getImages())
                .comments(board.getComments())
                .createdData(board.getCreatedData())
                .lastModifiedDate(board.getLastModifiedDate()).build();
    }

    private Board checkUnauthorizedAccess(Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        if(board.get().getUser()!=user){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return board.get();
    }
}
