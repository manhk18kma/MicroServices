package com.example.postservice.mapper;

import com.example.postservice.dto.request.PostCreationRequest;
import com.example.postservice.dto.response.PostResponse;
import com.example.postservice.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostCreationRequest request);
    PostResponse toPostResponse(Post post);
}
