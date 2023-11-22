package com.myblog9.service;

import com.myblog9.payload.CommentDto;
import com.myblog9.payload.CommentResponse;

public interface CommentService {

    CommentDto saveComment(CommentDto dto, long postId);

    CommentDto getCommentByPostId(int postId);

    CommentResponse getAllComments(int pageNo, int pageSize, String sortBy, String sortDir);

    CommentDto updateCommentById(CommentDto commentDto, long id);

    void deleteByCommentId(long id);
}
