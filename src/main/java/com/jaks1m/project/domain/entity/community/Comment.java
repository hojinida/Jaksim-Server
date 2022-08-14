package com.jaks1m.project.domain.entity.community;

import com.jaks1m.project.domain.entity.user.BaseEntity;
import com.jaks1m.project.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "COMMUNITY_SEQ_GENERATOR", sequenceName = "COMMUNITY_SEQ")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "COMMUNITY_SEQ_GENERATOR")
    private Long id;
    @NotEmpty
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Builder
    public Comment(String comment, User user, Board board) {
        this.comment = comment;
        this.user = user;
        this.board = board;
    }
}
