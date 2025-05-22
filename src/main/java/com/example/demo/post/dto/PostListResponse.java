package com.example.demo.post.dto;

import java.time.LocalDateTime;

import com.example.demo.post.entity.Post;

public record PostListResponse(
        Long postRowId,
        String title,
        Long memberRowId,
        int commentCount,
        LocalDateTime createdAt) {
    public static PostListResponse from(Post post) {
        return new PostListResponse(
                post.getId(),
                post.getTitle(),
                post.getMemberRowId(),
                post.getCommentCount(),
                post.getCreatedAt());
    }
}
