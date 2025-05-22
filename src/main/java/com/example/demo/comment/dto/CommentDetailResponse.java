package com.example.demo.comment.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.comment.entity.Comment;

public record CommentDetailResponse(
        Long commentRowId,
        String content,
        Long memberRowId,
        Long postRowId,
        Long parentRowId,
        boolean isReply,
        int childrenCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CommentDetailResponse> children) {
    public static CommentDetailResponse from(Comment comment) {
        return new CommentDetailResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMemberRowId(),
                comment.getPostEntity().getId(),
                comment.getParent() != null ? comment.getParent().getId() : null,
                comment.isReply(),
                comment.getChildrenCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getChildren().stream()
                        .map(CommentDetailResponse::from)
                        .collect(Collectors.toList()));
    }

    public static CommentDetailResponse fromWithoutChildren(Comment comment) {
        return new CommentDetailResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMemberRowId(),
                comment.getPostEntity().getId(),
                comment.getParent() != null ? comment.getParent().getId() : null,
                comment.isReply(),
                comment.getChildrenCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                List.of());
    }
}
