package com.example.postservice.service;

import com.example.postservice.dto.request.PostCreationRequest;
import com.example.postservice.dto.response.PostResponse;
import com.example.postservice.entity.Post;
import com.example.postservice.mapper.PostMapper;
import com.example.postservice.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    PostMapper postMapper;

    public PostResponse createPost(PostCreationRequest request){
        Post post = new Post();
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(request, post);
        post = postRepository.save(post);
        BeanUtils.copyProperties(post, postResponse);
        return postResponse;
    }

    public PostResponse getPost(String postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Comment not found"));
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);
        return postResponse;
    }

    public List<PostResponse> getAllPost(){
        var post = postRepository.findAll();
        return post.stream().map(postMapper::toPostResponse).toList();
    }
}
