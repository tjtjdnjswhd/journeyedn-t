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
    private Integer id;

    // 회원 아이디
    @Column(name = "account_id", nullable = false, unique = true, length = 20)
    private String accountId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false, length = 20)
    private String nickName;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isVisible;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Role role;

    @OneToMany(mappedBy = "user" , orphanRemoval = true)
    private List<Post> post = new ArrayList<>();

    @Builder
    public User(String accountId, String name, String nickName, String password, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isVisible) {
        this.accountId = accountId;
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isVisible = isVisible;
    }

    public static User of(String accountId, String nickname, String password) {
        return User.builder()
                .accountId(accountId)
                .nickName(nickname)
                .password(password)
                .createdAt(LocalDateTime.now())
                .isVisible(true)
                .build();
    }
}
