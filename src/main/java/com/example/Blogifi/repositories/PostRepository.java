package com.example.Blogifi.repositories;

import com.example.Blogifi.enteties.Post;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    // Using this EntityGraph So the Query get optimized
    @EntityGraph(value = "Post.tags")
    List<Post> findByTitle(String title);

    @EntityGraph(value = "Post.tags")
    List<Post> findByTagsName(String tagName);

    @EntityGraph(value = "Post.tags")
    @Override
    Page<Post> findAll(Pageable pagination);

    //@Query("SELECT p FROM posts p WHERE p.title = :title")
    //List<Post> findByTitleContaining(@Param("title") String title);
    @EntityGraph(value = "Post.tags")
    @Query("SELECT p FROM posts p WHERE p.title LIKE %:title%")
    List<Post> findByTitleContaining(@Param("title") String title);

    @Query(value = "SELECT * FROM posts WHERE title LIKE: %?1%", nativeQuery = true)
    List<Post> findByTitleNative(String title);

    @EntityGraph(value = "Post.tags")
    @Query("SELECT DISTINCT p FROM posts p LEFT JOIN p.tags t " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Post> searchPosts(@Param("search") String search);


}
