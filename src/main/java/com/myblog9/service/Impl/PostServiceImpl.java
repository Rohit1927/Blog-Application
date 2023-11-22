package com.myblog9.service.Impl;

import com.myblog9.entity.Post;
import com.myblog9.exception.ResourceNotFound;
import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;
import com.myblog9.repository.PostRepository;
import com.myblog9.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post =mapToPost(postDto);
        Post savedPost = postRepo.save(post);
        //  Post post =modelMapper.map(postDto,Post.class);
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());
        //Post save = postRepo.save(post);

        //PostDto dto=modelMapper.map(post,PostDto.class);
        //dto.setId(save.getId());
        //dto.setTitle(save.getTitle());
        //dto.setContent(save.getContent());
        //dto.setDescription(save.getDescription());
        return mapToDto(savedPost);
    }

    @Override
    public PostDto getPostByPostId(int postId) {
        Post post = postRepo.findById((long) postId).orElseThrow(() -> new ResourceNotFound("Post not found with id: " + postId));

        return mapToDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalElements((int) all.getTotalElements());
        postResponse.setLast(all.isLast());
        return postResponse;

    }
    //convert  dto to entity
    private Post mapToPost(PostDto postDto){
        Post post=modelMapper.map(postDto,Post.class);
        return post;
    }


    //convert entity object into dto
    private PostDto mapToDto(Post post){
        PostDto dto=modelMapper.map(post,PostDto.class);
       // PostDto mapToDto(Post post){
         //   PostDto dto=new PostDto();
           // dto.setId(post.getId());
            //dto.setTitle(post.getTitle());
         //   dto.setDescription(post.getDescription());
           // dto.setContent(post.getContent());
            return dto;
    }

    @Override
    public PostDto updateByPost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() ->
                new ResourceNotFound("Post is not found for id: " + id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postRepo.save(post);

        PostDto dto =modelMapper.map(savedPost, PostDto.class);
     //   dto.setId(savedPost.getId());
     //   dto.setTitle(savedPost.getTitle());
      //  dto.setDescription(savedPost.getDescription());
      //  dto.setContent(savedPost.getContent());
        return dto;
    }

    @Override
    public void deletePostById(int postId) {
        postRepo.deleteById((long) postId);
    }
}
