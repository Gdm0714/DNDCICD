package com.example.demo.post.service;

import com.example.demo.post.dto.CreatePostRequest;
import com.example.demo.post.dto.PostDetailResponse;
import com.example.demo.post.dto.PostListResponse;
import com.example.demo.post.dto.UpdatePostRequest;
import com.example.demo.post.entity.Post;
import com.example.demo.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long createPost(CreatePostRequest request) {
        Post postEntity = Post.of(
                request.title(),
                request.content(),
                request.memberRowId());
        Post savedEntity = postRepository.save(postEntity);
        return savedEntity.getId();
    }

    @Transactional
    public PostDetailResponse getPostById(Long postRowId) {
        Post postEntity = postRepository.findById(postRowId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + postRowId));

        return PostDetailResponse.from(postEntity);
    }

    @Transactional
    public PostDetailResponse updatePost(Long postRowId, UpdatePostRequest request) {
        Post postEntity = postRepository.findById(postRowId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + postRowId));

        postEntity.update(request.title(), request.content());

        return PostDetailResponse.from(postEntity);
    }

    @Transactional
    public void deletePost(Long postRowId) {
        if (!postRepository.existsById(postRowId)) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + postRowId);
        }
        postRepository.deleteById(postRowId);
    }

    public Page<PostListResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return posts.map(PostListResponse::from);
    }

    public Page<PostListResponse> searchPostsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByTitleContainingOrderByCreatedAtDesc(title, pageable);
        return posts.map(PostListResponse::from);
    }

    public Page<PostListResponse> searchPostsByMemberRowId(Long memberRowId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByMemberRowIdOrderByCreatedAtDesc(memberRowId, pageable);
        return posts.map(PostListResponse::from);
    }

    public Page<PostListResponse> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByTitleContainingOrContentContainingOrderByCreatedAtDesc(
                keyword, keyword, pageable);
        return posts.map(PostListResponse::from);
    }
}
