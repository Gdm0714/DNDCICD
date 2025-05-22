package com.example.demo.post.dto;

import java.time.LocalDateTime;

import com.example.demo.post.entity.Post;

public record PostDetailResponse(
        Long postRowId,
        String title,
        String content,
        Long memberRowId,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public static PostDetailResponse from(Post post) {
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMemberRowId(),
                post.getCommentCount(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }
}
