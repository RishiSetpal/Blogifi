package com.example.Blogifi.controller;

import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

    // AutoWired will Help to use the IOC (inversion of Control) i.e. Spring container objects direct binding to this object
    // instead of calling Post Constructor Post(its Parameters)
    // ex. new Post(int id, String title, String desc, LinkedHashSet<String> tags)

   @Autowired
   PostService postService;

    @GetMapping("")
    public Collection<Post> getAllPosts(){
//        return "All Posts";
        return postService.getAll();
    }

    @PostMapping("")
    public String createPosts(@RequestBody Post post){
//        return "Posts Created";
        return postService.create(post);
    }
}
