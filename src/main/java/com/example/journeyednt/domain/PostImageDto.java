package com.example.journeyednt.domain;

import com.example.journeyednt.entity.PostImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class PostImageDto{
    private Integer id;
    private String contentType;
    private byte[] data;
    private Boolean isPrimary;
    private Post post;

    // Entity를 DTO로 변환하는 정적 메서드
    public static PostImageDto fromEntity(PostImage postImage) {
        return PostImageDto.builder()
                .id(postImage.getId())
                .contentType(postImage.getContentType())
                .data(postImage.getData())
                .isPrimary(postImage.getIsPrimary())
                .post(postImage.getPost())
                .build();
    }

    // DTO를 Entity로 변환하는 메서드
    public PostImage toEntity() {
        return PostImage.builder()
                .contentType(this.contentType)
                .data(this.data)
                .isPrimary(this.isPrimary)
                .post(post)
                .build();
    }
}