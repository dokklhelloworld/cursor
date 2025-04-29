package com.hoon.cursor.service;

import com.hoon.cursor.dto.CommentRequestDTO;
import com.hoon.cursor.dto.CommentResponseDTO;
import com.hoon.cursor.entity.Comment;
import com.hoon.cursor.entity.Post;
import com.hoon.cursor.repository.CommentRepository;
import com.hoon.cursor.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(CommentResponseDTO::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommentResponseDTO createComment(Long postId, CommentRequestDTO commentDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        
        return CommentResponseDTO.convertToDTO(commentRepository.save(comment));
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

} 