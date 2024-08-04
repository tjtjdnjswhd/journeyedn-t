package com.example.journeyednt.domain.post;

import com.example.journeyednt.entity.Country;
import com.example.journeyednt.entity.Post;
import com.example.journeyednt.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isVisible;
    private Boolean isNotice;
    private User user;
    private List<String> tags;
    private Country country;
    private Integer rating;

    public static PostDto createEmpty() {
        return new PostDto();
    }

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .isVisible(post.getIsVisible())
                .isNotice(post.getIsNotice())
                .user(post.getUser())
                .tags(post.getTags())
                .country(post.getCountry())
                .rating(post.getRating())
                .build();
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .isVisible(true)
                .isNotice(this.isNotice)
                .tags(this.tags)
                .rating(this.rating)
                .user(user)
                .build();
    }
}
