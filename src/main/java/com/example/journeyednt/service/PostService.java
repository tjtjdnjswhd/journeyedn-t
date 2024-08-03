package com.example.journeyednt.service;

import com.example.journeyednt.domain.PostDto;
import com.example.journeyednt.entity.Country;
import com.example.journeyednt.entity.Post;
import com.example.journeyednt.entity.User;
import com.example.journeyednt.repository.CityRepository;
import com.example.journeyednt.repository.CountryRepository;
import com.example.journeyednt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    private static final String ORDER_BY_RECENT = "최신순";
    private static final String ORDER_BY_RATING = "평점순";

    // 게시글 생성
    @Transactional
    public PostDto createPost(PostDto postDto, User user) {
        // 주소드롭다운으로 가져와서 예외가 필요하지는 않지만 최소한의 유효성 검사를 진행하기 위해 작성!
        // city의 경우 country와 연관관계 매핑을 하고 있어 작성하지 않았다!
        Country country = countryRepository.findById(postDto.getCountry().getId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 시/군/구 아이디(이름)입니다."));

        Post post = Post.of(
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getIsNotice(),
                postDto.getTags(),
                postDto.getRating(),
                user // 유저 닉네임때문에 작성했다!
        );

        Post savedPost = postRepository.save(post);
        return PostDto.fromEntity(savedPost);
    }

    // 게시글 상세보기
    @Transactional(readOnly = true)
    public PostDto getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다. " + id));
        return PostDto.fromEntity(post);
    }

    // 게시글 수정
    @Transactional
    public PostDto updatePost(Integer id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. : " + id));

        Country country = countryRepository.findById(postDto.getCountry().getId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 시/군/구 아이디(이름)입니다."));

        post.updatePost(
                postDto.getTitle(),
                postDto.getContent(),
                LocalDateTime.now(),
                postDto.getIsVisible(),
                postDto.getTags(),
                postDto.getRating()
        );

        Post updatedPost = postRepository.save(post);
        return PostDto.fromEntity(updatedPost);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. : " + id));
        postRepository.delete(post);
    }

    // 모든 게시글의 공개 상태를 변경하는 벌크 업데이트
    @Transactional
    public int updateAllPostsVisibility(Boolean isVisible) {
        return postRepository.updateAllPostsVisibility(isVisible);
    }

    // 특정 사용자의 모든 게시글의 공개 상태를 변경하는 벌크 업데이트
    @Transactional
    public int updateUserPostsVisibility(Integer userId, Boolean isVisible) {
        return postRepository.updateUserPostsVisibility(userId, isVisible);
    }

    // 모든 공개게시글 찾기
    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts(String orderBy) {
        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            return postRepository.findAllPostsByOrderByCreatedAtDesc().stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return postRepository.findAllPostsByOrderByRatings().stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    // 제목으로 공개 게시물 찾기
    @Transactional(readOnly = true)
    public List<PostDto> findVisiblePostsByTitle(String title, String orderBy) {
        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            return postRepository.findByTitleAndIsVisibleTrueOrderByCreateAtDesc(title).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return postRepository.findByTitleAndIsVisibleTrueOrderByRatingDesc(title).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    // 특정 태그를 포함하는 게시글 찾기
    @Transactional(readOnly = true)
    public List<PostDto> getPostsByTag(String tag, String orderBy) {
        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            return postRepository.findByTagOrderByCreateAtDesc(tag).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return postRepository.findByTagOrderByRatingDesc(tag).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    // 시/도, 시/군/구로 공개 게시물 조회
    @Transactional(readOnly = true)
    public List<PostDto> getPostsByCityAndCountry(String cityName, String countryName, String orderBy) {
        Object[] ids = countryRepository.findCityIdAndCountryIdByName(countryName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 주소가 없습니다."));

        Integer cityId = (Integer) ids[0];
        Integer countryId = (Integer) ids[1];

        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            return postRepository.findByCityAndCountryOrderByCreateAt(cityId, countryId).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return postRepository.findByCityAndCountryOrderByRating(cityId, countryId).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    // 시/도, 시/군/구, 그리고 태그를 포함하는 공개게시물 조회
    @Transactional(readOnly = true)
    public List<PostDto> getPostsByCityCountryAndTag(String cityName, String countryName, String tag, String orderBy) {
        Object[] ids = countryRepository.findCityIdAndCountryIdByName(countryName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 주소가 없습니다."));

        Integer cityId = (Integer) ids[0];
        Integer countryId = (Integer) ids[1];

        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            return postRepository.findByCityCountryAndTagOrderByCreateAt(cityId, countryId, tag).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return postRepository.findByCityCountryAndTagOrderByRating(cityId, countryId, tag).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    // 시/도, 시/군/구, 그리고 제목을 포함하는 공개게시물 조회
    @Transactional(readOnly = true)
    public List<PostDto> getPostsByCityCountryAndTitle(String cityName, String countryName, String title, String orderBy) {
        Object[] ids = countryRepository.findCityIdAndCountryIdByName(countryName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 주소가 없습니다."));

        Integer cityId = (Integer) ids[0];
        Integer countryId = (Integer) ids[1];

        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            return postRepository.findByCityCountryAndTitleOrderByCreateAt(cityId, countryId, title).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return postRepository.findByCityCountryAndTitleOrderByRating(cityId, countryId, title).stream()
                    .map(PostDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }
}
