package com.jaks1m.project.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "RECEIVE_POLITY_SEQ_GENERATOR",
        sequenceName = "RECEIVE_POLITY_SEQ")
public class ReceivePolity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "RECEIVE_POLITY_SEQ_GENERATOR")
    private Long id;
    private Boolean receivePolity;//선택

    public void updateTos(boolean receivePolity){
        this.receivePolity=receivePolity;
    }
    public ReceivePolity(Boolean receivePolity){
        this.receivePolity=receivePolity;
    }
}
