package com.example.Blogifi.services;

import com.example.Blogifi.dtos.postDto.PostRequestDto;
import com.example.Blogifi.dtos.postDto.PostResponseDto;
import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.enteties.Tag;
import com.example.Blogifi.repositories.PostRepository;
import com.example.Blogifi.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

//    LinkedHashMap<Integer, Post> posts = new LinkedHashMap<>(
//            Map.of(
//                    2, new Post(2, "java", "java Desc"),
//                    1, new Post(1, "python", "py Desc")
//            )
//    );

//    Dependency Injection
//    1. Field Injection
//    2. Setter Injection
//    3. Interface Injection
//    4. Constructor Injection

// ToDo
//    Query Look
//    Projection

    // Constructor Injection
    @Autowired
    final private PostRepository postRepository;
    @Autowired
    final private TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public Post createPost(Post post) {
//        posts.put(post.getId(), post); //Returns void
//        return postRepository.save(post);

        // if tag is not present, Save inside tagRepository
        // Set<Tag> persistedTags = new HashSet<>();
        // for (Tag tag : post.getTags()) {
        //    Tag persistedTag = tagRepository.findByName(tag.getName());
        //    // if tag is not present, Save inside tagRepository
        //    // also update the Value of persistedTag
        //    if (persistedTag == null) {
        //        persistedTag = tagRepository.save(tag);
        //    }
        //    // directly use persistedTag value
        //    persistedTags.add(persistedTag);
        // }

        //Using Stream Updating Tags in Tags Table
        Set<Tag> persistedTags =
                post.getTags()
                        .stream()
                        .map(tag ->
                                //{
                                //    Tag persist = tagRepository.findByName(tag.getName());
                                //    return persist != null ? persist : tagRepository.save(tag);
                                //}
                                tagRepository.findByName(tag.getName()).orElseGet(()->tagRepository.save(tag))
                        ).collect(Collectors.toSet());
        post.setTags(persistedTags);

        /**
         * I thought
         * To just add Tag if the Tag is not Present in Tag Table (we don't need to get the Tags and set the same);
         post.getTags()
         .forEach(tag -> {
         Tag persist = tagRepository.findByName(tag.getName());
         if (persist == null) tagRepository.save(tag);
         }
         );
         * Need to Update Tag(id of Old persisted Tag //or else it will create a new Tag)
         * This Above Code in comment, won't work, as it will not use Old ID to keep the data same
         */

        return postRepository.save(post);
    }

    public List<Post> getAll() {
//        return posts.values(); //Returns Collection
        return postRepository.findAll();
    }

    public Post getpost(int id) {
//        Post post= posts.get(id);
//        if (post==null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with Id " + id+" Not Found");
//        }
//        return post;
        return postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User With " + id + "Not Found"));
    }

    public Post Update(int id, Post post) {
        getpost(id);
        post.setId(id);
//        posts.put(post.getId(),post); // Returns void
        Set<Tag> persistedTags =
                post.getTags()
                        .stream()
                        .map(tag -> {
                                    //Tag persist = tagRepository.findByName(tag.getName());
                                    //return persist != null ? persist : tagRepository.save(tag);
                                    return tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.save(tag));
                                }
                        )
                        .collect(Collectors.toSet());
        post.setTags(persistedTags);
        return postRepository.save(post);
    }

    public void delete(int id) {
//        getpost(id);
//        posts.remove(id);
//        getpost(id);
        // Before Deleting post we need to delete its Reference
        // Finding Post by ID
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with Id: " + id + " not Found."));
        // Setting Tags to Empty Set so its References are Removed
        post.setTags(new HashSet<>());
        postRepository.save(post); // This will Remove References

        // If we Directly Delete by ID then
        // Case 1: No Other Reference then it will Delete it from tags and posts_tags Table,
        // Case 2: Other References are Present, then  while Deleting it from tags Table, it will throw an Exception that Other References are Present
        postRepository.deleteById(id);
    }

    // Adding More QueryLookup getByPropertyName
    public List<Post> getByTitle(String title) {
        // return postRepository.findByTitle(title);
        return postRepository.findByTitleContaining(title);
        // return postRepository.findByTitleNative(title);
    }

    public List<Post> getByTagName(String tagName) {
        return postRepository.findByTagsName(tagName);
    }

    public List<Post> search(String value){
        return postRepository.searchPosts(value);
    }

    public Post ConvertToPost(PostRequestDto postRequestDto) {
//        Post post= new Post();
//        post.setTitle(postRequestDto.getTitle());
//        post.setDescription(postRequestDto.getDescription());
//        return post;

//        return new Post(
//                0,
//                postRequestDto.getTitle(),
//                postRequestDto.getDescription(),
//                postRequestDto.getTags()
//                        .stream()
//                        .map(tag -> new Tag(tag.toLowerCase()))
//                        //.map(Tag::new) // Short hand Lambda Expression
//                        .collect(
//                                Collectors.toSet())
//        );
        Post post= new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setDescription(postRequestDto.getDescription());
        post.setTags(postRequestDto.getTags()
                        .stream()
                        .map(tag -> new Tag(tag.toLowerCase()))
                        .collect(Collectors.toSet()));
        return post;
    }

    public PostResponseDto ConvertToPostResponse(Post post) {
//        return new PostResponseDto(
//                post.getId(),
//                post.getTitle(),
//                post.getDescription(),
//                post.getTags()
//                        .stream()
//                        .map(tag -> tag.getName())
//                        //.map(Tag::getName) //[Improved/Same] Short hand Lambda Expression
//                        .collect(Collectors.toSet())
//        );
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()),
                post.getCreatedDateTime(),
                post.getLastModifiedDateTime()
        );
    }

}
