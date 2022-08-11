package com.jaks1m.project.domain.entity.follow;

import com.jaks1m.project.domain.entity.user.BaseEntity;
import com.jaks1m.project.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "FOLLOW_SEQ_GENERATOR", sequenceName = "FOLLOW_INFORMATION_SEQ")
public class Follow extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "FOLLOW_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_USER_ID")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_USER_ID")
    private User toUser;

    @Builder
    public Follow(User fromUser, User toUser) {
        this.fromUser=fromUser;
        this.toUser=toUser;
    }
}
