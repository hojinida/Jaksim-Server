package com.jaks1m.project.domain.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "FRIENDS_SEQ_GENERATOR", sequenceName = "FRIENDS_INFORMATION_SEQ")
public class Friends extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "FRIENDS_INFORMATION_SEQ_GENERATOR")
    private Long id;
    private Long friendId;
    @Builder
    public Friends(Long friendId) {
        this.friendId = friendId;
    }
}
