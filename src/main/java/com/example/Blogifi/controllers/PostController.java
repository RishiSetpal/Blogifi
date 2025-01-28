package com.example.Blogifi.controllers;

import com.example.Blogifi.dtos.postDto.PostRequestDto;
import com.example.Blogifi.dtos.postDto.PostResponseDto;
import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.enteties.Tag;
import com.example.Blogifi.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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

    // Constructor
    PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Get all posts", description = "Retrieve a list of all posts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts")
    })
    @GetMapping("")
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "200") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        // return "All Posts";
        // return postService.getAll();
        // return new ResponseEntity<List<Post>>(postService.getAll(), HttpStatus.OK); // Returns List of Posts
        // This will Return List ResponseDto

        Page<PostResponseDto> posts = postService.getAll(page, size, sortDirection, sortBy).map(postService::ConvertToPostResponse);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Create a new post", description = "Create a new post with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        // return "Posts Created";
        // postService.create(post);
        // return "Post Created Successfully";
        Post postResponse = postService.createPost(postService.ConvertToPost(postRequestDto));
        return new ResponseEntity<PostResponseDto>(postService.ConvertToPostResponse(postResponse), HttpStatus.CREATED);
    }

    @Operation(summary = "Get post by ID", description = "Retrieve a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post by ID"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    // PathParam - /@PathVarible
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> GetPostById(@PathVariable int id) {
        // return new ResponseEntity<Post>(postService.getpost(id), HttpStatus.CREATED); // Previously it was Returning Post
        return new ResponseEntity<PostResponseDto>(postService.ConvertToPostResponse(postService.getpost(id)), HttpStatus.CREATED);
    }

    @Operation(summary = "Update post by ID", description = "Update the post with the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    // Path Param - @PathVariable
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

    @Operation(summary = "Partially update post by ID", description = "Partially update a post with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> UpdatePartialPostById(@PathVariable int id, @RequestBody PostRequestDto postRequestDto) {
        Post oldpost = postService.getpost(id);
        oldpost.setTitle(postRequestDto.getTitle() != null ? postRequestDto.getTitle() : oldpost.getTitle());
        oldpost.setDescription(postRequestDto.getDescription() != null ? postRequestDto.getTitle() : oldpost.getDescription());
//         oldpost.setTags(post.getTags() !=null ? post.getTags() : oldpost.getTags());
        oldpost.setTags(!postRequestDto.getTags().isEmpty()
                ?
                postRequestDto.getTags()
                        .stream()
                        .map(name -> new Tag(name))
                        .collect(Collectors.toSet())
                : oldpost.getTags());
        Post postResponse = postService.Update(id, oldpost);
        return new ResponseEntity<PostResponseDto>(postService.ConvertToPostResponse(postResponse), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete post by ID", description = "Delete a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get posts by title", description = "Retrieve posts by the given title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by title")
    })
    //Tags can be Get From Tag Repositories ? but wont be able to get post
    @GetMapping("/title")
    public ResponseEntity<List<PostResponseDto>> getPostByTitle(@RequestParam String value) {
        return new ResponseEntity<List<PostResponseDto>>(
                postService.getByTitle(value).stream().map(post -> postService.ConvertToPostResponse(post)).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get posts by tag", description = "Retrieve posts by the given tag name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by tag")
    })
    @GetMapping("/tag")
    public ResponseEntity<List<PostResponseDto>> getPostByTagName(@RequestParam String value) {
        return new ResponseEntity<List<PostResponseDto>>(
                postService.getByTagName(value).stream().map(post -> postService.ConvertToPostResponse(post)).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> searchPosts(@RequestParam String value){
        List<Post> searchResult = postService.search(value);
        List<PostResponseDto> Posts = searchResult.stream().map(postService::ConvertToPostResponse).toList();
        return ResponseEntity.ok(Posts);
    }



}
