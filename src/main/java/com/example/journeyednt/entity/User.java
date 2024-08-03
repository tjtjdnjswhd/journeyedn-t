package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @Column(name = "account_id", nullable = false, unique = true, length = 20)
    private String accountId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "nickname", unique = true, nullable = false, length = 20)
    private String nickName;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isVisible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> post = new ArrayList<>();

    @Builder
    public User(Integer id, String accountId, String name, String nickName, String passwordHash, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isVisible, Role role, List<Post> post) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.nickName = nickName;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isVisible = isVisible;
        this.role = role;
        this.post = post;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
