package com.jaks1m.project.heart.domain;


import com.jaks1m.project.board.domain.Board;
import com.jaks1m.project.common.domain.BaseEntity;
import com.jaks1m.project.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "COMMUNITY_SEQ_GENERATOR", sequenceName = "COMMUNITY_SEQ")
public class Heart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "COMMUNITY_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Heart(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
