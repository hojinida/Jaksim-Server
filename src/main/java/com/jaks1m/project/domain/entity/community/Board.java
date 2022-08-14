package com.jaks1m.project.domain.entity.community;

import com.jaks1m.project.domain.entity.aws.S3Image;
import com.jaks1m.project.domain.entity.user.BaseEntity;
import com.jaks1m.project.domain.entity.user.Status;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.dto.community.response.BoardResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ")
//@Where(clause = "status='ACTIVE'")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long id;
    private String title;
    @Lob
    private String content;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<S3Image> s3Images=new ArrayList<>();
    private Long countVisit;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
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
        s3Images.forEach(s3Image -> images.add(s3Image.getImagePath()));
        return images;
    }
    public List<String> getKeys(){
        List<String> keys=new ArrayList<>();
        s3Images.forEach(s3Image -> keys.add(s3Image.getImageKey()));
        return keys;
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

    public BoardResponse toBoardResponse(){
        return BoardResponse.builder()
                .boardId(id)
                .title(title)
                .content(content)
                .userName(user.getName().getName())
                .visit(countVisit)
                .images(getImages())
                .createdData(this.getCreatedData())
                .lastModifiedDate(this.getLastModifiedDate()).build();
    }
}
