package com.myblog9.service.Impl;

import com.myblog9.entity.Comment;
import com.myblog9.entity.Post;
import com.myblog9.exception.ResourceNotFound;
import com.myblog9.payload.CommentDto;
import com.myblog9.payload.CommentResponse;
import com.myblog9.repository.CommentRepository;
import com.myblog9.repository.PostRepository;
import com.myblog9.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;

    private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto saveComment(CommentDto dto, long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId));
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(savedComment.getId());
        commentDto.setName(savedComment.getName());
        commentDto.setEmail(savedComment.getEmail());
        commentDto.setBody(savedComment.getBody());
        return commentDto;
    }

    @Override
    public CommentDto getCommentByPostId(int postId) {
        Comment comment = commentRepo.findById((long) postId).orElseThrow(() -> new ResourceNotFound("Post not found with id: " + postId));

        return mapToDto(comment);
    }

    @Override
    public CommentResponse getAllComments(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortDir).ascending()
                : Sort.by(sortDir).descending();
        Pageable peageble = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Comment> all = commentRepo.findAll(peageble);
        List<Comment> comments = all.getContent();
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

        CommentResponse commentResponse=new CommentResponse();
        commentResponse.setContent(commentDtos);
        commentResponse.setPageNo(all.getNumber());
        commentResponse.setPageSize(all.getSize());
        commentResponse.setTotalElements((int) all.getTotalElements());
        commentResponse.setTotalPages(all.getTotalPages());
        commentResponse.setLast(all.isLast());
        return commentResponse;
    }


    //convert entity object into dto
    CommentDto mapToDto(Comment comment){
        CommentDto dto=new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }

    @Override
    public CommentDto updateCommentById(CommentDto commentDto, long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Comment not found with id:  " + id));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment savedComment = commentRepo.save(comment);

        CommentDto dto =new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());
        return dto;

    }

    @Override
    public void deleteByCommentId(long id) {
        commentRepo.deleteById(id);
    }
}
