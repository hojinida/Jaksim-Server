package com.jaks1m.project.domain.entity.community;


import com.jaks1m.project.domain.entity.user.BaseEntity;
import com.jaks1m.project.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "COMMUNITY_SEQ_GENERATOR", sequenceName = "COMMUNITY_SEQ")
@Where(clause = "status='ACTIVE'")
public class Heart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "COMMUNITY_SEQ_GENERATOR")
    @Column(name = "BOARD_ID")
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
