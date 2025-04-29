package com.hoon.cursor.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.hoon.cursor.entity.Comment;


@Getter
@Setter
public class CommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public static CommentResponseDTO convertToDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
} 