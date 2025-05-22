package com.example.demo.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.demo.comment.dto.CommentDetailResponse;
import com.example.demo.comment.dto.CommentRowIdResponse;
import com.example.demo.comment.dto.CreateCommentRequest;
import com.example.demo.comment.dto.UpdateCommentRequest;
import com.example.demo.comment.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     */
    @PostMapping("/v1/posts/{postRowId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "댓글 생성 API")
    public CommentRowIdResponse createComment(
            @PathVariable("postRowId") @Parameter(name = "postRowId", description = "게시물 ID", required = true) Long postRowId,
            @RequestBody @Valid CreateCommentRequest request) {
        Long commentRowId = commentService.createComment(postRowId, request);
        return new CommentRowIdResponse(commentRowId);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/v1/comments/{commentRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "댓글 수정 API")
    public CommentDetailResponse updateComment(
            @PathVariable("commentRowId") @Parameter(name = "commentRowId", description = "댓글 ID", required = true) Long commentRowId,
            @RequestBody @Valid UpdateCommentRequest request) {
        return commentService.updateComment(commentRowId, request);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/v1/comments/{commentRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "댓글 삭제 API")
    public void deleteComment(
            @PathVariable("commentRowId") @Parameter(name = "commentRowId", description = "댓글 ID", required = true) Long commentRowId) {
        commentService.deleteComment(commentRowId);
    }

    /**
     * 특정 게시물의 모든 댓글 조회
     */
    @GetMapping("/v1/posts/{postRowId}/comments")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시물의 댓글 목록 조회 API")
    public List<CommentDetailResponse> getCommentsByPost(
            @PathVariable("postRowId") @Parameter(name = "postRowId", description = "게시물 ID", required = true) Long postRowId,
            @RequestParam(defaultValue = "false") boolean topLevelOnly) {

        if (topLevelOnly) {
            return commentService.getTopLevelCommentsByPostId(postRowId);
        } else {
            return commentService.getCommentsByPostId(postRowId);
        }
    }

    /**
     * 특정 댓글의 대댓글 조회
     */
    @GetMapping("/v1/comments/{commentRowId}/replies")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "대댓글 조회 API")
    public List<CommentDetailResponse> getRepliesByComment(
            @PathVariable("commentRowId") @Parameter(name = "commentRowId", description = "댓글 ID", required = true) Long commentRowId) {
        return commentService.getRepliesByCommentId(commentRowId);
    }

    /**
     * 댓글 상세 조회
     */
    @GetMapping("/v1/comments/{commentRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "댓글 상세 조회 API")
    public CommentDetailResponse getComment(
            @PathVariable("commentRowId") @Parameter(name = "commentRowId", description = "댓글 ID", required = true) Long commentRowId) {
        return commentService.getCommentById(commentRowId);
    }

    /**
     * 특정 게시물의 댓글 개수 조회
     */
    @GetMapping("/v1/posts/{postRowId}/comments/count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시물의 댓글 개수 조회 API")
    public Long getCommentCount(
            @PathVariable("postRowId") @Parameter(name = "postRowId", description = "게시물 ID", required = true) Long postRowId) {
        return commentService.getCommentCountByPostId(postRowId);
    }
}
