package com.example.journeyednt.domain;

import com.example.journeyednt.entity.Country;
import com.example.journeyednt.entity.Post;
import com.example.journeyednt.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean isVisible;
    private Boolean isNotice;
    private User user;
    private List<String> tags;
    private Country country;
    private Integer rating;

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .updateAt(post.getUpdateAt())
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
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .isVisible(true)
                .isNotice(this.isNotice)
                .tags(this.tags)
                .rating(this.rating)
                .user(user)
                .build();
    }
}
