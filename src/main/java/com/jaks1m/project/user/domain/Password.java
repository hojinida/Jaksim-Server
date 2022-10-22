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
public class Password extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_INFORMATION_SEQ_GENERATOR")
    private Long id;
    @Column(length = 100)//영문자 20자 이내
    @NotEmpty
    private String password;
    public void updatePassword(String password){
        this.password=password;
    }
    public Password(String password){this.password=password;}
}
