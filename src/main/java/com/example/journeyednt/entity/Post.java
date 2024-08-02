package com.example.journeyednt.entity;

import com.example.journeyednt.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "is_notice", nullable = false)
    private Boolean isNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "tag")
    @Convert(converter = StringListConverter.class)
    private List<String> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cdd_id")
    private Country country;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Builder
    public Post(Integer id, String title, String content, LocalDateTime createAt, LocalDateTime updateAt, Boolean isVisible, Boolean isNotice, List<String> tags, Integer rating, User user, Country country) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isVisible = isVisible;
        this.isNotice = isNotice;
        this.tags = tags;
        this.rating = rating;
        this.user = user;
        this.country = country;
    }

    public void updatePost(String title, String content, LocalDateTime updateAt, Boolean isVisible, List<String> tags, Integer rating) {
        this.title = title;
        this.content = content;
        this.updateAt = updateAt;
        this.isVisible = isVisible;
        this.tags = tags;
        this.rating = rating;
    }

    public static Post of(String title, String content, Boolean isNotice, List<String> tags, Integer rating, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .isNotice(isNotice)
                .tags(tags)
                .rating(rating)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .isVisible(true)
                .user(user)
                .build();
    }
}