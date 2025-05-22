package com.example.demo.post.controller;

import com.example.demo.post.dto.*;
import com.example.demo.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시물 생성
     */
    @PostMapping("/v1")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "게시물 생성 API")
    public PostRowIdResponse createPost(@RequestBody @Valid CreatePostRequest request) {
        Long postRowId = postService.createPost(request);
        return new PostRowIdResponse(postRowId);
    }

    /**
     * 게시물 상세 조회
     */
    @GetMapping("/v1/{postRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시물 상세 조회 API")
    public PostDetailResponse getPost(
            @PathVariable("postRowId") @Parameter(name = "postRowId", description = "게시물 ID", required = true) Long postRowId) {
        return postService.getPostById(postRowId);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/v1/{postRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시물 수정 API")
    public PostDetailResponse updatePost(
            @PathVariable("postRowId") @Parameter(name = "postRowId", description = "게시물 ID", required = true) Long postRowId,
            @RequestBody @Valid UpdatePostRequest request) {
        return postService.updatePost(postRowId, request);
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/v1/{postRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시물 삭제 API")
    public void deletePost(
            @PathVariable("postRowId") @Parameter(name = "postRowId", description = "게시물 ID", required = true) Long postRowId) {
        postService.deletePost(postRowId);
    }

    /**
     * 모든 게시물 조회 (페이징)
     */
    @GetMapping("/v1")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시물 목록 조회 API")
    public Page<PostListResponse> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return postService.getAllPosts(page, size);
    }

    /**
     * 게시물 검색
     */
    @GetMapping("/v1/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시물 검색 API")
    public Page<PostListResponse> searchPosts(
            @RequestParam String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if ("title".equals(type)) {
            return postService.searchPostsByTitle(keyword, page, size);
        } else {
            // 기본값: 제목 또는 내용 검색
            return postService.searchPosts(keyword, page, size);
        }
    }

    /**
     * 특정 회원의 게시물 조회
     */
    @GetMapping("/v1/member/{memberRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 회원의 게시물 조회 API")
    public Page<PostListResponse> getPostsByMember(
            @PathVariable("memberRowId") @Parameter(name = "memberRowId", description = "회원 ID", required = true) Long memberRowId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return postService.searchPostsByMemberRowId(memberRowId, page, size);
    }
}
