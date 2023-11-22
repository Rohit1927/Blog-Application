package com.myblog9.service;

import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);
    
    PostDto getPostByPostId(int postId);
    
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    
    PostDto updateByPost(PostDto postDto, long id);


    void deletePostById(int postId);
}

