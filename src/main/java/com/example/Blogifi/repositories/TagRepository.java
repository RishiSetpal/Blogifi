package com.example.Blogifi.repositories;

import com.example.Blogifi.enteties.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    Tag findByName(String name);
}
