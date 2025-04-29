package com.hoon.cursor.service;

import com.hoon.cursor.dto.CommentResponseDTO;
import com.hoon.cursor.dto.PostRequestDTO;
import com.hoon.cursor.dto.PostResponseDTO;
import com.hoon.cursor.entity.Comment;
import com.hoon.cursor.entity.Post;
import com.hoon.cursor.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return convertToDTO(post);
    }

    public PostResponseDTO createPost(PostRequestDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return convertToDTO(postRepository.save(post));
    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        
        return convertToDTO(postRepository.save(post));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostResponseDTO convertToDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setComments(post.getComments().stream().map(comment -> CommentResponseDTO.convertToDTO(comment)).toList());
        return dto;
    }
} 