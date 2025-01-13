package com.example.Blogifi.services;

import com.example.Blogifi.enteties.Post;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    LinkedHashMap<Integer, Post> posts = new LinkedHashMap<>(
            Map.of(
                    2, new Post(2, "java", "java Desc", new LinkedHashSet<>(Set.of("spring", "java developer", "it"))),
                    1, new Post(1, "python", "py Desc", new LinkedHashSet<>(Set.of("Python developer", "it")))
            )
    );

    public String create(Post post) {
        posts.put(post.getId(), post);
        return null;
    }

    public Collection<Post> getAll() {
        return posts.values();
    }
}
