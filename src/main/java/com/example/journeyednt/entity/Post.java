package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Builder
    public Post(String title, String content, LocalDateTime createAt, LocalDateTime updateAt, Boolean isVisible, Boolean isNotice) {
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isVisible = isVisible;
        this.isNotice = isNotice;
    }

    public void updatePost(String title, String content, Boolean isVisible) {
        this.title = title;
        this.content = content;
        this.isVisible = isVisible;
    }
}
