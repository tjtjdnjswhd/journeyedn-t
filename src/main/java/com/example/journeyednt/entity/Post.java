package com.example.journeyednt.entity;

import com.example.journeyednt.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "is_notice", nullable = false)
    private Boolean isNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "tags")
    @Convert(converter = StringListConverter.class)
    private List<String> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cdd_id")
    private Country country;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Builder
    public Post(Integer id, String title, String content, Boolean isVisible, Boolean isNotice, List<String> tags, Integer rating, User user, Country country) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isVisible = isVisible;
        this.isNotice = isNotice;
        this.tags = tags;
        this.rating = rating;
        this.user = user;
        this.country = country;
    }

    public void updatePost(String title, String content, Boolean isVisible, List<String> tags, Integer rating) {
        this.title = title;
        this.content = content;
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
                .isVisible(true)
                .user(user)
                .build();
    }
}