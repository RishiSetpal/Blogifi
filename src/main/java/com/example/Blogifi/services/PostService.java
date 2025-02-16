package com.example.Blogifi.services;

import com.example.Blogifi.dtos.postDto.PostRequestDto;
import com.example.Blogifi.dtos.postDto.PostResponseDto;
import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.enteties.Tag;
import com.example.Blogifi.repositories.PostRepository;
import com.example.Blogifi.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    // Dependency Injection
    // 1. Field Injection
    // 2. Setter Injection
    // 3. Interface Injection
    // 4. Constructor Injection
    // TODO - Query Look | Projection

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
        //Using Stream Updating Tags in Tags Table
        Set<Tag> persistedTags =
                post.getTags()
                        .stream()
                        .map(tag ->
                                tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.save(tag))
                        ).collect(Collectors.toSet());
        post.setTags(persistedTags);

        return postRepository.save(post);
    }

    public Page<Post> getAll(int page, int size, String sortDirection, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return postRepository.findAll(pageable);
    }

    public Post getpost(int id) {
        return postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User With " + id + "Not Found"));
    }

    public Post Update(int id, Post post) {
        getpost(id);
        post.setId(id);
        Set<Tag> persistedTags =
                post.getTags()
                        .stream()
                        .map(tag -> tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.save(tag)))
                        .collect(Collectors.toSet());
        post.setTags(persistedTags);
        return postRepository.save(post);
    }

    public void delete(int id) {
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
        return postRepository.findByTitleContaining(title);
    }

    public List<Post> getByTagName(String tagName) {
        return postRepository.findByTagsName(tagName);
    }

    public List<Post> search(String value) {
        return postRepository.searchPosts(value);
    }

    public Post ConvertToPost(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setDescription(postRequestDto.getDescription());
        post.setTags(postRequestDto.getTags()
                .stream()
                .map(tag -> new Tag(tag.toLowerCase()))
                .collect(Collectors.toSet()));
        return post;
    }

    public PostResponseDto ConvertToPostResponse(Post post) {
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
