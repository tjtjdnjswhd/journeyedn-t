package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_image")

public class PostImage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public PostImage(String contentType, byte[] data, boolean isPrimary) {
        this.contentType = contentType;
        this.data = data;
        this.isPrimary = isPrimary;
    }

    public void updatePostImage(String contentType, byte[] data, boolean isPrimary) {
        this.contentType = contentType;
        this.data = data;
        this.isPrimary = isPrimary;
    }
}

