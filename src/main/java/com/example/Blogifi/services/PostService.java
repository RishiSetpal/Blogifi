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

// Dependency Injection
// 1. Field Injection
// 2. Setter Injection
// 3. Interface Injection
// 4. Constructor Injection
// TODO - Query Look | Projection

@Service
public class PostService {

    // Constructor Injection
    @Autowired
    final private PostRepository postRepository;
    @Autowired
    final private TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    //Returns previously added tag with its id orElseGet( tagRepositoy.save(newTag) );
    private Set<Tag> persistTags(Set<Tag> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findByName(tag.getName()).orElseGet(()-> tagRepository.save(tag)))
                .collect(Collectors.toSet());
    }

    public Post createPost(Post post) {
        post.setTags(persistTags(post.getTags()));
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
        post.setTags(persistTags(post.getTags()));
        return postRepository.save(post);
    }

    // If we Directly Delete by ID then
    // Case 1: No Other Reference then it will Delete it from tags and posts_tags Table,
    // Case 2: Other References are Present, then  while Deleting it from tags Table, it will throw an Exception that Other References are Present
    //
    // Before Deleting post we need to delete its Reference
    public void delete(int id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with Id: " + id + " not Found."));
        post.setTags(new HashSet<>()); // Setting Tags to Empty Set so its References are Removed
        postRepository.save(post); // This will Remove References
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
