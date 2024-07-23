package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//아무런 매개변수가 없는 생성자를 생성하되 다른 패키지에 소속된 클래스는 접근을 불허한다
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private int id;

    // 회원 아이디
    @Column(name = "account_id", nullable = false, unique = true, length = 20)
    private String account_id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "nickname", unique = true, nullable = false, length = 20)
    private String nickname;

    @Column(name = "passwordHash", nullable = false)
    private String password;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "isVisible", nullable = false)
    private boolean isVisible;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Role role;

    @OneToMany(mappedBy = "user" , orphanRemoval = true)
    // 추후 계정 삭제시 cascade = CascadeType.ALL
    private List<Post> post = new ArrayList<>();

    @Builder
    public User(String account_id, String name, String nickname, String password, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isVisible) {
        this.account_id = account_id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isVisible = isVisible;
    }

    public static User of(String account_id, String nickname, String password) {
        return User.builder()
                .account_id(account_id)
                .nickname(nickname)
                .password(password)
                .createdAt(LocalDateTime.now())
                .isVisible(true)
                .build();
    }

// 나중에 수정 할때 추가 하기
//    public User update(String nickname) {
//        this.nickname = nickname;
//        this.updatedAt = LocalDateTime.now();
//        return this;
//    }
//
//    public void setEncryptedPassword(String encryptedPassword) {
//        this.password = encryptedPassword;
//    }
}
