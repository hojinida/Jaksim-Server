package com.jaks1m.project.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;


@Entity @Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "USER_SEQ_GENERATOR",
                    sequenceName = "USER_SEQ")
public class User extends BaseEntity implements UserDetails{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_SEQ_GENERATOR")
    @Column(name = "USER_ID")
    private Long id;

    @Column(length = 50)//영문자 50자 이내
    @NotEmpty
    private String email;
    @Column(length = 10)
    @NotNull
    private Boolean enabled;
    @Column(length = 10)
    @NotNull
    private String wSigned;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role=Role.USER;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "PASSWORD_ID")
    private Password password;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "NAME_ID")
    private Name name;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "PRIVACY_POLITY_ID")
    private PrivacyPolity privacyPolity;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "TERMS_OF_SERVICE_ID")
    private TermsOfService termsOfService;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "RECEIVE_POLITY_ID")
    private ReceivePolity receivePolity;

    public void updateEmail(String email){
        this.email=email;
    }
    public void updatePassword(String password){
        this.password.updatePassword(password);
    }

    public void updateName(String name){
        this.name.updateName(name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect=new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.getKey();
            }
        });
        return collect;
    }
    @Override
    public String getUsername() {return email;}

    @Override
    public String getPassword(){return password.getPassword();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Builder
    public User(String email, String password, String name
            , Boolean privacyPolity, Boolean termsOfService, Boolean receivePolity
            , Boolean enabled ,Role role,String wSigned){
        this.email=email;
        this.password=new Password(password);
        this.name=new Name(name);
        this.privacyPolity=new PrivacyPolity(privacyPolity);
        this.termsOfService=new TermsOfService(termsOfService);
        this.receivePolity=new ReceivePolity(receivePolity);
        this.enabled=enabled;
        this.role=role;
        this.wSigned=wSigned;
    }
}
