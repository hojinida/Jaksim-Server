package com.jaks1m.project.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "PASSWORD_SEQ_GENERATOR",
        sequenceName = "PASSWORD_SEQ")
public class Password extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PASSWORD_SEQ_GENERATOR")
    private Long id;
    @Column(length = 100)//영문자 20자 이내
    @NotEmpty
    private String password;
    public void updatePassword(String password){
        this.password=password;
    }
    public Password(String password){this.password=password;}
}
