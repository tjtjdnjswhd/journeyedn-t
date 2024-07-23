package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(nullable = false, length = 30) // 길이 변경 필요!
    private String title;

    @Column(nullable = false, length = 65535) // 해당 내용 회의 필요! 데이터타입 변경?
    private String content;

//    @Lob CLOB(Character Large Object) 타입으로 매핑, 매우 큰 텍스트 데이터를 저장
//    @Column(nullable = false)
//    private String content;
//
//    @Column(nullable = false, columnDefinition = "TEXT") 매우 큰 문자열을 저장
//    private String content;
//
//    @Column(nullable = false, columnDefinition = "LONGTEXT") 최대 4GB의 텍스트를 저장
//    private String content;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    @Column(name = "is_notice", nullable = false)
    private boolean isNotice;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Builder
    public Post(String title, String content, LocalDateTime createAt, LocalDateTime updateAt, boolean isVisible, boolean isNotice, Post post) {
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isVisible = isVisible;
        this.isNotice = isNotice;
    }

    public void updatePost(String title, String content, boolean isVisible) {
        this.title = title;
        this.content = content;
        this.isVisible = isVisible;
    }
}
