package com.jaks1m.project.domain.entity.community;

import com.jaks1m.project.domain.entity.aws.S3Image;
import com.jaks1m.project.domain.entity.user.BaseEntity;
import com.jaks1m.project.domain.entity.user.Status;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.dto.community.response.BoardResponse;
import com.jaks1m.project.dto.community.response.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "COMMUNITY_SEQ_GENERATOR", sequenceName = "COMMUNITY_SEQ")
@Where(clause = "status='ACTIVE'")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "COMMUNITY_SEQ_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long id;
    private String title;
    @Lob
    private String content;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<S3Image> s3Images=new ArrayList<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> comments=new ArrayList<>();
    private Long countVisit;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateBoard(String title, String content, BoardType boardType){
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }

    public void updateStatus(Status status){
        super.updateStatus(status);
    }

    public void updateVisit(){
        this.countVisit+=1;
    }

    public List<String> getImages(){
        List<String> images=new ArrayList<>();
        for(S3Image image:s3Images){
            images.add(image.getImagePath());
        }
        return images;
    }

    public List<CommentResponseDto> getComments(){
        List<CommentResponseDto> commentDto=new ArrayList<>();
        for(Comment comment:comments){
            commentDto.add(CommentResponseDto.builder().comment(comment.getComment()).name(comment.getUser().getName().getName())
                    .image(comment.getUser().getS3Image().getImagePath()).createdData(comment.getCreatedData()).lastModifiedDate(comment.getLastModifiedDate()).build());
        }
        return commentDto;
    }
    @Builder
    public Board(String title, String content, List<S3Image> s3Images, Long countVisit, BoardType boardType, User user) {
        this.title = title;
        this.content = content;
        this.s3Images = s3Images;
        this.countVisit = countVisit;
        this.boardType = boardType;
        this.user = user;
    }
}
