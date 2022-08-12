package com.jaks1m.project.domain.entity.user;

import com.jaks1m.project.domain.entity.aws.S3Image;
import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.follow.Follow;
import com.jaks1m.project.domain.entity.notification.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity @Getter
@RequiredArgsConstructor
@SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ")
@Where(clause = "status='ACTIVE'")
public class User extends BaseEntity implements UserDetails{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_SEQ_GENERATOR")
    @Column(name = "USER_ID")
    private Long id;
    private String email;
    private String homeGround;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role=Role.USER;

    @OneToMany(mappedBy = "fromUser",cascade = CascadeType.ALL)
    private List<Follow> follows =new ArrayList<>();

    @OneToMany(mappedBy = "toUser",cascade = CascadeType.ALL)
    private List<Follow> followers =new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Board> boards=new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications=new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "S3_IMAGE_ID")
    private S3Image s3Image;
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
    public void updatePassword(String password){
        this.password.updatePassword(password);
    }

    public void updateName(String name){
        this.name.updateName(name);
    }

    public void updateReceivePolity(Boolean receivePolity){this.receivePolity.updateTos(receivePolity);}

    public void updateImage(String key,String path){
        this.s3Image.updateKey(key);
        this.s3Image.updatePath(path);
    }
    @Override
    public void updateStatus(Status status){
        super.updateStatus(status);
        s3Image.updateStatus(status);
        password.updateStatus(status);
        name.updateStatus(status);
        privacyPolity.updateStatus(status);
        termsOfService.updateStatus(status);
        receivePolity.updateStatus(status);
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
    public User(String email, String password, String name,S3Image s3Image, Boolean privacyPolity
            , Boolean termsOfService, Boolean receivePolity,Role role,String homeGround){
        this.email=email;
        this.password=new Password(password);
        this.name=new Name(name);
        this.s3Image=s3Image;
        this.privacyPolity=new PrivacyPolity(privacyPolity);
        this.termsOfService=new TermsOfService(termsOfService);
        this.receivePolity=new ReceivePolity(receivePolity);
        this.role=role;
        this.homeGround=homeGround;
    }

}
