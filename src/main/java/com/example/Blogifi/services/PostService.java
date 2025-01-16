package com.example.Blogifi.services;

import com.example.Blogifi.dtos.postDto.PostRequestDto;
import com.example.Blogifi.dtos.postDto.PostResponseDto;
import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PostService {

//    LinkedHashMap<Integer, Post> posts = new LinkedHashMap<>(
//            Map.of(
//                    2, new Post(2, "java", "java Desc"),
//                    1, new Post(1, "python", "py Desc")
//            )
//    );

    @Autowired
    PostRepository postRepository;

    public Post createPost(Post post) {
//        posts.put(post.getId(), post); //Returns void
        return postRepository.save(post);
    }

    public List<Post> getAll() {
//        return posts.values(); //Returns Collection
        return postRepository.findAll();
    }

    public Post getpost(int id){
//        Post post= posts.get(id);
//        if (post==null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with Id " + id+" Not Found");
//        }
//        return post;
        return postRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User With " +id+ "Not Found"));
    }

    public Post Update(int id,Post post){
        getpost(id);
        post.setId(id);
//        posts.put(post.getId(),post); // Returns void
        return postRepository.save(post);

    }

    public void delete(int id){
        getpost(id);
//        posts.remove(id);
        postRepository.deleteById(id);
    }

    public Post ConvertToPost(PostRequestDto postRequestDto){
        Post post= new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setDescription(postRequestDto.getDescription());
        return post;
    }

    public PostResponseDto ConvertToPostResponse(Post post, String message){
        return new PostResponseDto(post.getId(), post.getTitle(), post.getDescription(), message);
    }

}
