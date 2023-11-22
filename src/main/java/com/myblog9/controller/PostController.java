package com.myblog9.controller;

import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;
import com.myblog9.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //https://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,
                                        BindingResult result

    ) {
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //https://localhost:8080/api/posts/{postId}
    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable int postId) {
        PostDto dto = postService.getPostByPostId(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=id&sortDir=asc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value ="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        PostResponse postResponse = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updateByPost(@RequestBody PostDto postDto, @PathVariable long id){
        PostDto dto = postService.updateByPost(postDto, id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/{postId}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}")
    public ResponseEntity<String>deletePostById(@PathVariable int postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post is deleted with id: "+postId,HttpStatus.OK);
    }

}
