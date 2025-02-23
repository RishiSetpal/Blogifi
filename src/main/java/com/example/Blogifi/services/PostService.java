package com.example.Blogifi.services;

import com.example.Blogifi.dtos.postDto.PostRequestDto;
import com.example.Blogifi.dtos.postDto.PostResponseDto;
import com.example.Blogifi.dtos.userDto.UserAuthorResponseDto;
import com.example.Blogifi.enteties.Post;
import com.example.Blogifi.enteties.Tag;
import com.example.Blogifi.enteties.User;
import com.example.Blogifi.repositories.PostRepository;
import com.example.Blogifi.repositories.TagRepository;
import com.example.Blogifi.repositories.UserRepository;
import jakarta.transaction.Transactional;
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

    @Autowired
    final private UserService userService;
    final private UserRepository userRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository, UserService userService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //Returns previously added tag with its id orElseGet( tagRepositoy.save(newTag) );
    private Set<Tag> persistTags(Set<Tag> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.save(tag)))
                .collect(Collectors.toSet());
    }

    public Post createPost(Post post, int userId) {
        User user = userService.getById(userId); // get user by I'd
        post.setUser(user); // set user in post // So that post will be created with user ID and user added in database with post.
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

    // Previously: we were taking previousPostId and Replacing with the newPostObject
    // Now we want to getUserObjectById and
    public Post Update(int id, Post post, int userId) {
        User user = userService.getById(userId);
        Post existingPost = user.getPosts().stream()
                .filter(dbUserPosts -> dbUserPosts.getId() == id).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User With " + id + "Not Found"));
        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }
        if (post.getDescription() != null) {
            existingPost.setDescription(post.getDescription());
        }
        if (post.getTags() != null){
            existingPost.setTags(persistTags(post.getTags()));
        }
        return postRepository.save(existingPost);
    }

    // If we Directly Delete by ID then
    // Case 1: No Other Reference then it will Delete it from tags and posts_tags Table,
    // Case 2: Other References are Present, then  while Deleting it from tags Table, it will throw an Exception that Other References are Present
    //
    // Before Deleting post we need to delete its Reference
    @Transactional
    public void delete(int id, int userId) {
            //Removing this as we are searching user Created post if we don't found then it will be NotFound
            //Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with Id: " + id + " not Found."));
            //postRepository.save(existingPost); // This will Remove References
            //postRepository.deleteById(id);

        User user = userService.getById(userId);
        Post existingPost = user.getPosts()
                .stream()
                .filter(post -> post.getId() == id)
                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with Id: " + id + " not Found."));
        existingPost.setTags(new HashSet<>()); // Setting Tags to Empty Set so its References are Removed
            //if post is not present in user then it will also get removed from post
            //List<Post> newPost = user.getPosts().stream().filter(post -> post.getId() != id).toList(); // new List<post> without postWithIdProvided
            //user.setPosts(newPost);
        user.getPosts().removeIf(post -> post.getId() == id);
        postRepository.delete(existingPost);
        userRepository.save(user);
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
        // User user = post.getUser();
        // UserAuthorResponseDto userAuthorResponseDto = new UserAuthorResponseDto();
        // userAuthorResponseDto.setId(user.getId());
        // userAuthorResponseDto.setUsername(user.getUsername());

        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()),
                convertToUserAuthorResponse(post.getUser()),
                post.getCreatedDateTime(),
                post.getLastModifiedDateTime()
        );

    }

    public UserAuthorResponseDto convertToUserAuthorResponse(User user){
        UserAuthorResponseDto userAuthorResponseDto = new UserAuthorResponseDto();
        userAuthorResponseDto.setId(user.getId());
        userAuthorResponseDto.setUsername(user.getUsername());
        return userAuthorResponseDto;
    }

}
