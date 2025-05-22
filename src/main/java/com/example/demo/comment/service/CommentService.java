package com.example.demo.comment.service;

import com.example.demo.comment.dto.CommentDetailResponse;
import com.example.demo.comment.dto.CreateCommentRequest;
import com.example.demo.comment.dto.UpdateCommentRequest;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.post.entity.Post;
import com.example.demo.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createComment(Long postRowId, CreateCommentRequest request) {
        Post post = postRepository.findById(postRowId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + postRowId));

        Comment parentComment = null;
        if (request.parentRowId() != null) {
            parentComment = commentRepository.findById(request.parentRowId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다. ID: " + request.parentRowId()));

            if (!parentComment.getPostEntity().getId().equals(postRowId)) {
                throw new IllegalArgumentException("부모 댓글이 해당 게시물에 속하지 않습니다.");
            }
        }

        Comment commentEntity = Comment.of(
                request.content(),
                request.memberRowId(),
                post,
                parentComment
        );

        Comment savedEntity = commentRepository.save(commentEntity);
        return savedEntity.getId();
    }

    @Transactional
    public CommentDetailResponse updateComment(Long commentRowId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentRowId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + commentRowId));

        comment.update(request.content());

        return CommentDetailResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long commentRowId) {
        if (!commentRepository.existsById(commentRowId)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + commentRowId);
        }
        commentRepository.deleteById(commentRowId);
    }

    public List<CommentDetailResponse> getCommentsByPostId(Long postRowId) {
        if (!postRepository.existsById(postRowId)) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + postRowId);
        }

        List<Comment> comments = commentRepository.findByPostEntityIdOrderByCreatedAtAsc(postRowId);
        return comments.stream()
                .map(CommentDetailResponse::fromWithoutChildren)
                .collect(Collectors.toList());
    }

    public List<CommentDetailResponse> getTopLevelCommentsByPostId(Long postRowId) {
        if (!postRepository.existsById(postRowId)) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + postRowId);
        }

        List<Comment> comments = commentRepository.findByPostEntityIdAndParentIsNullOrderByCreatedAtAsc(postRowId);
        return comments.stream()
                .map(CommentDetailResponse::from)
                .collect(Collectors.toList());
    }

    public List<CommentDetailResponse> getRepliesByCommentId(Long commentRowId) {
        if (!commentRepository.existsById(commentRowId)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + commentRowId);
        }

        List<Comment> replies = commentRepository.findByParentIdOrderByCreatedAtAsc(commentRowId);
        return replies.stream()
                .map(CommentDetailResponse::from)
                .collect(Collectors.toList());
    }

    public long getCommentCountByPostId(Long postRowId) {
        return commentRepository.countByPostEntityId(postRowId);
    }

    public CommentDetailResponse getCommentById(Long commentRowId) {
        Comment comment = commentRepository.findById(commentRowId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + commentRowId));

        return CommentDetailResponse.from(comment);
    }
}