package com.example.journeyednt.domain.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreate {

    @NotNull(message = "제목을 작성해주세요")
    @NotEmpty(message = "제목을 작성해주세요")
    private String title;

    @NotNull(message = "본문을 작성해주세요")
    @NotEmpty(message = "본문을 작성해주세요")
    private String content;

    @NotNull(message = "평점을 설정해주세요")
    @Range(min = 1, max = 5, message = "평점은 1 ~ 5 만 선택 가능합니다")
    private Integer rating;

    private List<String> tags;

    private MultipartFile primaryImage;

    private List<MultipartFile> images;

    @NotNull(message = "지역을 선택해주세요")
    private Integer countryId;
}
