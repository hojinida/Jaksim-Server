package com.jaks1m.project.domain.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "USER_INFORMATION_SEQ_GENERATOR", sequenceName = "USER_INFORMATION_SEQ")
@Where(clause = "status='ACTIVE'")
public class ReceivePolity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_INFORMATION_SEQ_GENERATOR")
    private Long id;
    private Boolean receivePolity;//선택

    public void updateTos(boolean receivePolity){
        this.receivePolity=receivePolity;
    }
    public ReceivePolity(Boolean receivePolity){
        this.receivePolity=receivePolity;
    }
}
