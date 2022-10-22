package com.jaks1m.project.user.domain;

import com.jaks1m.project.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "USER_INFORMATION_SEQ_GENERATOR", sequenceName = "USER_INFORMATION_SEQ")
@Where(clause = "status='ACTIVE'")
public class Name extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_INFORMATION_SEQ_GENERATOR")
    private Long id;
    @Column(length = 30)//한글 10자 이내
    @NotEmpty
    private String name;

    public void updateName(String name){
        this.name=name;
    }

    public Name(String name){this.name=name;}
}
