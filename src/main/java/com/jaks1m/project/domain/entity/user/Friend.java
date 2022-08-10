package com.jaks1m.project.domain.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "FRIENDS_SEQ_GENERATOR", sequenceName = "FRIENDS_INFORMATION_SEQ")
public class Friend extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "FRIENDS_INFORMATION_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User friend;
    @Builder
    public Friend(User friend) {
        this.friend=friend;
    }
}
