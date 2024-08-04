package com.example.journeyednt.domain.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreate {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Integer rating;

    private List<String> tags;

    private MultipartFile primaryImage;

    private List<MultipartFile> images;

    @NotNull
    private Integer countryId;


}
