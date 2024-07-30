package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.Id;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "is_notice", nullable = false)
    private Boolean isNotice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(Integer id,String title, String content, LocalDateTime createAt, LocalDateTime updateAt, Boolean isVisible, Boolean isNotice) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isVisible = isVisible;
        this.isNotice = isNotice;
    }

    public void updatePost(String title, String content, LocalDateTime updateAt, Boolean isVisible) {
        this.title = title;
        this.content = content;
        this.updateAt = updateAt;
        this.isVisible = isVisible;
    }

    public static Post of(String title, String content, Boolean isNotice) {
        return Post.builder()
                .title(title)
                .content(content)
                .isNotice(isNotice)
                .build();
    }
}
