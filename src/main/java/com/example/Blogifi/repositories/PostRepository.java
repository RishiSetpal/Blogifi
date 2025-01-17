package com.example.Blogifi.repositories;

import com.example.Blogifi.enteties.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
