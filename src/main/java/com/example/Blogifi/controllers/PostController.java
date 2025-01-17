package com.example.Blogifi.controllers;

import com.example.Blogifi.dtos.postDto.PostRequestDto;
import com.example.Blogifi.dtos.postDto.PostResponseDto;
import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    // AutoWired will Help to use the IOC (inversion of Control) i.e. Spring container objects direct binding to this object
    // instead of calling Post Constructor Post(its Parameters) it auto Construction Injection
    // ex. new Post(int id, String title, String desc, LinkedHashSet<String> tags)

    // making it final and private so that it can not be accessed by others directly.
    @Autowired
    final private PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        // return "All Posts";
        // return postService.getAll();
        // return new ResponseEntity<List<Post>>(postService.getAll(), HttpStatus.OK); // Returns List of Posts
        // This will Return List ResponseDto
        return new ResponseEntity<List<PostResponseDto>>(
                postService.getAll()
                        .stream()
                        .map(post -> postService.ConvertToPostResponse(post))
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        // return "Posts Created";
        // postService.create(post);
        // return "Post Created Successfully";
        Post postResponse = postService.createPost(postService.ConvertToPost(postRequestDto));
        return new ResponseEntity<PostResponseDto>(postService.ConvertToPostResponse(postResponse), HttpStatus.CREATED);
    }

    // PathParam - /@PathVarible
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> GetPostById(@PathVariable int id) {
        // return new ResponseEntity<Post>(postService.getpost(id), HttpStatus.CREATED); // Previously it was Returning Post
        return new ResponseEntity<PostResponseDto>(postService.ConvertToPostResponse(postService.getpost(id)), HttpStatus.CREATED);
    }

    // QueryParam - @
    @PutMapping("/{id}")
    public ResponseEntity<String> UpdatePostById(@PathVariable int id, @RequestBody Post post) {
        postService.Update(id, post);
        return new ResponseEntity<>("Post Updated Successfully", HttpStatus.CREATED);
    }

    // Additional using QueryParam - ?id=@RequestParam
    @PutMapping
    public ResponseEntity<String> UpdatePostByIdQuery(@RequestParam int id, @RequestBody Post post) {
        postService.Update(id, post);
        return new ResponseEntity<>("Post Updated Successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> UpdatePartialPostById(@PathVariable int id, @RequestBody PostRequestDto postRequestDto) {
        Post oldpost = postService.getpost(id);
        oldpost.setTitle(postRequestDto.getTitle() != null ? postRequestDto.getTitle() : oldpost.getTitle());
        oldpost.setDescription(postRequestDto.getDescription() != null ? postRequestDto.getTitle() : oldpost.getDescription());
        // oldpost.setTags(post.getTags() !=null ? post.getTags() : oldpost.getTags());
        Post postResponse = postService.Update(id, oldpost);
        return new ResponseEntity<PostResponseDto>(postService.ConvertToPostResponse(postResponse), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
