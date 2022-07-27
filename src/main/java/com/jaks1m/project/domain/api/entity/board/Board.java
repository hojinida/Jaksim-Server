package com.jaks1m.project.domain.api.entity.board;

import com.jaks1m.project.domain.api.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity @Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    private String subtitle;

    private String content;



    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateBoard(String title, String subtitle, String content, BoardType boardType){
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.boardType = boardType;
    }

    @Builder
    public Board(String title, String subtitle, String content, BoardType boardType, User user) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.boardType = boardType;
        this.user = user;
        this.user.getBoards().add(this);
    }
}
