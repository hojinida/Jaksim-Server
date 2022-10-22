package com.jaks1m.project.board.domain;

import com.jaks1m.project.aws.domain.S3Image;
import com.jaks1m.project.common.domain.BaseEntity;
import com.jaks1m.project.comment.domain.Comment;
import com.jaks1m.project.heart.domain.Heart;
import com.jaks1m.project.user.domain.Status;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.comment.application.dto.CommentResponse;
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

    private String bracket;
    @Lob
    private String content;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<S3Image> s3Images=new ArrayList<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> comments=new ArrayList<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Heart> hearts=new ArrayList<>();
    private Long countVisit;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateBoard(String title,String bracket , String content, BoardType boardType){
        this.title = title;
        this.bracket=bracket;
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

    public List<CommentResponse> getComments(){
        List<CommentResponse> commentDto=new ArrayList<>();
        for(Comment comment:comments){
            commentDto.add(CommentResponse.builder().id(comment.getId()).comment(comment.getComment()).name(comment.getUser().getName().getName())
                    .image(comment.getUser().getS3Image().getImagePath()).createdData(comment.getCreatedData()).lastModifiedDate(comment.getLastModifiedDate()).build());
        }
        return commentDto;
    }
    @Builder
    public Board(String title, String bracket , String content, List<S3Image> s3Images, Long countVisit, BoardType boardType, User user) {
        this.title = title;
        this.bracket=bracket;
        this.content = content;
        this.s3Images = s3Images;
        this.countVisit = countVisit;
        this.boardType = boardType;
        this.user = user;
    }
}
