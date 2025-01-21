package com.example.Blogifi.repositories;

import com.example.Blogifi.enteties.Post;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @EntityGraph(value = "Post.tags")
    List<Post> findByTitle(String title);

    @EntityGraph(value = "Post.tags")
    List<Post> findByTagsName(String tagName);

    @EntityGraph(value = "Post.tags")
    @Override
    List<Post> findAll();
}
