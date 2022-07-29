package com.jaks1m.project.domain.api.entity.board;

import com.jaks1m.project.domain.api.entity.user.BaseEntity;
import com.jaks1m.project.domain.api.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity @Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;
    @Lob
    private String content;
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

    public void updateVisit(){
        this.countVisit+=1;
    }

    public void updateUser(User user){
        this.user=user;
        this.user.getBoards().add(this);
    }

    @Builder
    public Board(String title, String content, Long countVisit, BoardType boardType, User user) {
        this.title = title;
        this.content = content;
        this.countVisit = countVisit;
        this.boardType = boardType;
        updateUser(user);
    }
}
