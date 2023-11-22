package com.myblog9.controller;

import com.myblog9.payload.CommentDto;
import com.myblog9.payload.CommentResponse;
import com.myblog9.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //https://localhost:8080/api/comments
    @PostMapping("{postId}")
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto,@PathVariable long postId){
        CommentDto dto = commentService.saveComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("{postId}")
    public ResponseEntity<CommentDto>getCommentByPostId(@PathVariable int postId) {
        CommentDto dto = commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
//http://localhost:8080/api/comments?pageNo=0&pageSize=3&sortBy=id&sortDir=asc
    @GetMapping
    public CommentResponse getAllComments(
            @RequestParam(value ="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir


    ) {
        CommentResponse commentReponse = commentService.getAllComments(pageNo,pageSize,sortBy,sortDir);
        return commentReponse;
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentDto>updateCommentById(@RequestBody CommentDto commentDto,@PathVariable long id){
        CommentDto dto = commentService.updateCommentById(commentDto, id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteByCommentId(@PathVariable long id){
        commentService.deleteByCommentId(id);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }
}
