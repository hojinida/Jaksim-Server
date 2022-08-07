package com.jaks1m.project.domain.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
public class Friends {
    @Id
    private Long id;

    private String name;
    @Builder
    public Friends(Long id,String name) {
        this.id = id;
        this.name=name;
    }
}
