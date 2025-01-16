package com.example.Blogifi.services;

import com.example.Blogifi.enteties.Post;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PostService {

    LinkedHashMap<Integer, Post> posts = new LinkedHashMap<>(
            Map.of(
                    2, new Post(2, "java", "java Desc"),
                    1, new Post(1, "python", "py Desc")
            )
    );

    public void create(Post post) {
        posts.put(post.getId(), post);
    }

    public Collection<Post> getAll() {
        return posts.values();
    }


    public Post getpost(int id){
        Post post= posts.get(id);
        if (post==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with Id " + id+" Not Found");
        }
        return post;
    }

    public void Update(int id,Post post){
        getpost(id);
        post.setId(id);
        posts.put(post.getId(),post);
    }

    public void delete(int id){
        getpost(id);
        posts.remove(id);
    }

}
