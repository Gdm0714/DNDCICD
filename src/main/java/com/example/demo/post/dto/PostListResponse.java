package com.example.demo.post.dto;

import com.example.demo.post.entity.Post;

import java.time.LocalDateTime;

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
