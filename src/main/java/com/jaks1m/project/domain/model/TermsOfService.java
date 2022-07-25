package com.jaks1m.project.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "TERMS_OF_SERVICE_SEQ_GENERATOR",
        sequenceName = "TERMS_OF_SERVICE_SEQ")
public class TermsOfService extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "TERMS_OF_SERVICE_SEQ_GENERATOR")
    private Long id;
    private Boolean termsOfService;//필수
    public void updateTos(boolean termsOfService){
        this.termsOfService=termsOfService;
    }
    public TermsOfService(Boolean termsOfService){
        this.termsOfService=termsOfService;
    }
}
