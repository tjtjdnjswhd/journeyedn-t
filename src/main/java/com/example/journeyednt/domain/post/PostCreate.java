package com.example.journeyednt.domain.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @NotNull
    private Integer countryId;
}
