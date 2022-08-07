package com.jaks1m.project.domain.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "USER_INFORMATION_SEQ_GENERATOR",
        sequenceName = "PRIVACY_POLITY_SEQ")
public class PrivacyPolity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_INFORMATION_SEQ_GENERATOR")
    private Long id;
    private Boolean privacyPolity;//필수

    public void updateTos(boolean privacyPolity){
        this.privacyPolity=privacyPolity;
    }
    public PrivacyPolity(Boolean privacyPolity){this.privacyPolity=privacyPolity;}
}
