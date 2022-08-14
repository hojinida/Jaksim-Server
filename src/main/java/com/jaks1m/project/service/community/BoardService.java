package com.jaks1m.project.service.community;

import com.jaks1m.project.domain.entity.aws.S3Image;
import com.jaks1m.project.dto.community.request.BoardAddRequestDto;
import com.jaks1m.project.dto.community.response.BoardResponse;
import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.community.BoardType;
import com.jaks1m.project.domain.entity.user.Status;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.dto.community.response.ImageDto;
import com.jaks1m.project.repository.func.BoardRepository;
import com.jaks1m.project.repository.func.S3ImageRepository;
import com.jaks1m.project.repository.user.UserRepository;
import com.jaks1m.project.config.security.SecurityUtil;
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
    private final S3ImageRepository s3ImageRepository;
    @Transactional
    public void addBoard(List<ImageDto> imageDto, BoardAddRequestDto request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board=Board.builder().user(user).boardType(request.getBoardType()).content(request.getContent())
                .title(request.getTitle()).countVisit(0L).build();
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
    public BoardResponse editBoard(List<ImageDto> imageDto, BoardAddRequestDto request, Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        board.get().updateBoard(request.getTitle(), request.getContent(), request.getBoardType());
        s3ImageRepository.deleteAll(board.get().getS3Images());
        saveS3Image(imageDto,board.get());
        
        return createBoardResponse(board.get());
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

    private void saveS3Image(List<ImageDto> imageDto,Board board){
        if(!imageDto.isEmpty()) {
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
                .content(board.getContent())
                .userName(board.getUser().getName().getName())
                .visit(board.getCountVisit())
                .images(board.getImages())
                .createdData(board.getCreatedData())
                .lastModifiedDate(board.getLastModifiedDate()).build();
    }
}
