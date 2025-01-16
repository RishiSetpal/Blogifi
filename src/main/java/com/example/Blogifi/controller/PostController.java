package com.example.Blogifi.controller;

import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

    // AutoWired will Help to use the IOC (inversion of Control) i.e. Spring container objects direct binding to this object
    // instead of calling Post Constructor Post(its Parameters) it auto Construction Injection
    // ex. new Post(int id, String title, String desc, LinkedHashSet<String> tags)

    // making it final and private so that it can not be accessed by others directly.
    @Autowired
    final private PostService postService;

    PostController(PostService postService){
    this.postService=postService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<Post>> getAllPosts(){
//        return "All Posts";
//        return postService.getAll();
        return new ResponseEntity<Collection<Post>>(postService.getAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createPosts(@RequestBody Post post){
//        return "Posts Created";
        postService.create(post);
//        return "Post Created Successfully";
        return new ResponseEntity<String>("Post Created Successfully",HttpStatus.CREATED);
    }

//    PathParam - /@PathVarible
    @GetMapping("/{id}")
    public ResponseEntity<Post> GetPostById(@PathVariable int id){
        return new ResponseEntity<Post>(postService.getpost(id), HttpStatus.CREATED);
    }

//    QueryParam - @
    @PutMapping("/{id}")
    public ResponseEntity<String> UpdatePostById(@PathVariable int id, @RequestBody Post post){
        postService.Update(id,post);
        return new ResponseEntity<>("Post Updated Successfully", HttpStatus.CREATED );
    }

//    Additional using QueryParam - ?id=@RequestParam
    @PutMapping
    public ResponseEntity<String> UpdatePostByIdQuery(@RequestParam int id, @RequestBody Post post) {
        postService.Update(id, post);
        return new ResponseEntity<>("Post Updated Successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> UpdatePartialPostById(@PathVariable int id,@RequestBody Post post ){
        Post oldpost=postService.getpost(id);
        oldpost.setTitle(post.getTitle() !=null ? post.getTitle():oldpost.getTitle());
        oldpost.setDesc(post.getDesc() !=null ? post.getTitle() :oldpost.getDesc());
//        oldpost.setTags(post.getTags() !=null ? post.getTags() : oldpost.getTags());
        postService.Update(id,oldpost);
        return new ResponseEntity<>("Post Updated Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
