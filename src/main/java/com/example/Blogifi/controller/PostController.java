package com.example.Blogifi.controller;

import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

   @Autowired
   PostService postService;

    @GetMapping("")
    public Collection<Post> getAllPosts(){
        return postService.getAll();
//        return "All Posts";
    }

    @PostMapping("")
    public String createPosts(@RequestBody Post post){
        return postService.create(post);
//        return "Posts Created";
    }
}
