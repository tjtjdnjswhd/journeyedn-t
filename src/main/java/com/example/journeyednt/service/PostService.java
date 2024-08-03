package com.example.journeyednt.service;

import com.example.journeyednt.domain.post.PostCreate;
import com.example.journeyednt.domain.post.PostDto;
import com.example.journeyednt.entity.Country;
import com.example.journeyednt.entity.Post;
import com.example.journeyednt.entity.User;
import com.example.journeyednt.repository.CountryRepository;
import com.example.journeyednt.repository.PostRepository;
import com.example.journeyednt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final int POST_QUERY_COUNT = 12;

    private final PostRepository postRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    public static final String ORDER_BY_RECENT = "최신순";

    // 게시글 생성
    @Transactional
    public PostDto createPost(PostCreate postCreate, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디입니다"));
        Country country = countryRepository.findById(postCreate.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 시/군/구 아이디(이름)입니다."));

        Post post = Post.of(
                postCreate.getTitle(),
                postCreate.getContent(),
                false,
                postCreate.getTags(),
                postCreate.getRating(),
                user
        );

        Post savedPost = postRepository.save(post);
        postRepository.updateCddId(savedPost.getId(), country.getId());
        return PostDto.fromEntity(savedPost);
    }

    @Transactional
    public PostDto createNotice(String title, String content, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디입니다"));
        Post post = Post.of(
                title,
                content,
                true,
                null,
                5,
                user
        );

        Post savedPost = postRepository.save(post);
        return PostDto.fromEntity(savedPost);
    }

    // 게시글 수정
    @Transactional
    public PostDto updatePost(Integer id, PostCreate postCreate) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. :" + id));

        Country country = countryRepository.findById(postCreate.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 시/군/구 아이디(이름)입니다."));

        post.updatePost(
                postCreate.getTitle(),
                postCreate.getContent(),
                LocalDateTime.now(),
                false,
                postCreate.getTags(),
                postCreate.getRating()
        );

        Post updatedPost = postRepository.save(post);
        postRepository.updateCddId(id, country.getId());
        return PostDto.fromEntity(updatedPost);
    }

    @Transactional
    public void invisiblePost(Integer id) {
        postRepository.updatePostVisibility(id, false);
    }

    // 게시글 상세보기
    @Transactional(readOnly = true)
    public PostDto getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다. :" + id));
        return PostDto.fromEntity(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getNotices(int count) {
        Pageable pageable = PageRequest.of(1, count);
        List<Post> posts = postRepository.findByIsNoticeTrue(pageable);
        return posts.stream().map(PostDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPosts(int page, String orderBy) {
        Pageable pageable = PageRequest.of(page, POST_QUERY_COUNT);
        List<Post> posts;
        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            posts = postRepository.findByIsVisibleTrueOrderByCreateAtDesc(pageable);
        } else {
            posts = postRepository.findByIsVisibleTrueOrderByRatingDesc(pageable);
        }

        return posts.stream().map(PostDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPosts(String text, int page, String orderBy) {
        Pageable pageable = PageRequest.of(page, POST_QUERY_COUNT);
        List<Post> posts;
        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            posts = postRepository.findByIsVisibleTrueAndByTextOrderByCreateAtDesc(text, pageable);
        } else {
            posts = postRepository.findByIsVisibleTrueAndByTextOrderByRatingDesc(text, pageable);
        }

        return posts.stream().map(PostDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPosts(int countryId, String text, int page, String orderBy) {
        Pageable pageable = PageRequest.of(page, POST_QUERY_COUNT);
        List<Post> posts;
        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            posts = postRepository.findByCountryIdAndIsVisibleTrueAndByTextOrderByCreateAtDesc(countryId, text, pageable);
        } else {
            posts = postRepository.findByCountryIdAndIsVisibleTrueAndByTextOrderByRatingDesc(countryId, text, pageable);
        }

        return posts.stream().map(PostDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPosts(int countryId, int page, String orderBy) {
        Pageable pageable = PageRequest.of(page, POST_QUERY_COUNT);
        List<Post> posts;
        if (orderBy == null || orderBy.equals(ORDER_BY_RECENT)) {
            posts = postRepository.findByCountryIdAndIsVisibleTrueOrderByCreateAtDesc(countryId, pageable);
        } else {
            posts = postRepository.findByCountryIdAndIsVisibleTrueOrderByRatingDesc(countryId, pageable);
        }

        return posts.stream().map(PostDto::fromEntity).toList();
    }
}
